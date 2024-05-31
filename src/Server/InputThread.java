package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import Header.ObjectMessage;

public class InputThread implements Runnable {

	// private UserStaus userStaus;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket;
	private Vector<UserData> userDatas;
	private UserData userData;
	private Main mContext;
	private OutputMessage outputMessage;
	Thread thread;

	public InputThread(Socket socket, Main mContext, Vector<UserData> userDatas) {
		this.mContext = mContext;
		this.socket = socket;
		this.userDatas = userDatas;

	}

	@Override
	public void run() {
		userData = new UserData();
		try {
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			userData.setOos(oos);
			userDatas.add(userData);
			for (UserData userData : userDatas) {
				if (userData.getOos() == this.oos) {
					this.userData = userData;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
			ObjectMessage objectMessage;
			while ((objectMessage = (ObjectMessage) ois.readObject()) != null) {
				userData.setName(objectMessage.getName());
				userData.setTarget(objectMessage.getTarget());
				userData.setMessage(objectMessage.getMessage());
				System.out.println(userData.toString());
				messagePush();
			}

		} catch (Exception e) {
			e.printStackTrace();
			userDatas.remove(userData);
			upDateUser();
		}
	}

	private void messagePush() {
		String name = userData.getName();
		String[] targets = userData.getTarget();
		String message = userData.getMessage();
		if (name != null && targets != null && !message.isEmpty()) {
			chatMessagePush(name, targets, message);
		} else if (name != null && targets != null && message.isEmpty()) {
			addRoom(targets);
		} else if (name != null && targets == null && message.isEmpty()) {// targets == null 이면 처음 접속 유저 현재 유저목록 푸시
			upDateUser();
		}
	}

	private void chatMessagePush(String name, String[] targets, String message) {
		for (UserData userData2 : userDatas) {
			for (int i = 0; i < targets.length; i++) {
				if (targets[i].toString().equals(userData2.getName())) {
					Thread thread = new Thread(
							new OutputMessage(
									new ObjectMessage(name, targets, message), userData2.getOos()));
					thread.start();
					try {
						thread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void addRoom(String[] targets) {

		for (UserData userData2 : userDatas) {
			for (int i = 0; i < targets.length; i++) {
				if (targets[i].toString().equals(userData2.getName())) {
					Thread thread = new Thread(new OutputMessage(
							new ObjectMessage(targets, ""), userData2.getOos()));
					thread.start();
					try {
						thread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	private void upDateUser() {
		String[] users = new String[userDatas.size()];
		for (UserData userData2 : userDatas) {
			ObjectOutputStream oos2 = userData2.getOos();
			for (int i = 0; i < userDatas.size(); i++) {
				users[i] = userDatas.get(i).getName();
			}
			Thread thread = new Thread(new OutputMessage(new ObjectMessage(users), oos2));
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class OutputMessage implements Runnable {
		private ObjectMessage objectMessage;
		private ObjectOutputStream oos2;

		public OutputMessage(ObjectMessage objectMessage, ObjectOutputStream oos2) {
			this.objectMessage = objectMessage;
			this.oos2 = oos2;
		}
		@Override
		public void run() {

			try {
				oos2.writeObject(objectMessage);
				oos2.flush();
				System.out.println("데이터 전송 서버에서");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// mContext.usersStatus.remove(userStaus);
			}
		}

	}


}
