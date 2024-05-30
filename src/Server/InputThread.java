package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import Header.ObjectMessage;
import Header.StatusCode;
import Header.UserInfo;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
			ObjectMessage objectMessage;
			while ((objectMessage = (ObjectMessage) ois.readObject()) != null) {
				System.out.println("사용자로부터 데이터 받음");
				userData.setName(objectMessage.getName());
				userData.setTarget(objectMessage.getTarget());
				userData.setMessage(objectMessage.getMessage());

				System.out.println(userData.toString());
				// newUserConnect(objectMessage);
				messagePush();
			}

		} catch (Exception e) {
			// 유저 커넥 끊김 캐치 부분
			e.printStackTrace();
			userDatas.remove(userData);
			upDateUser();
			// upDateUser();
		}
	}

	private void messagePush() {
		String[] targets;
		if ((targets = userData.getTarget()) != null) {
			for (String targetName : targets) {
				if (userData.getName().equals(targetName)) {// 타켓 유저한테만 푸시
					// 여기서 푸시

				}
			}
		} else {// targets == null 이면 처음 접속 유저 현재 유저목록 푸시
			upDateUser();
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

//	
//		
//		
//		if (usersStaus.size() > 0) {
//			for (UserStaus userStaus : usersStaus) {
//				if (userStaus.getIp().equals(socket.getInetAddress().toString())) {
//					this.userStaus = userStaus;
//					try {
//						userStaus.setOos(new ObjectOutputStream(socket.getOutputStream()));
//						System.out.println(userStaus.getOos().toString());
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			userStaus = new UserStaus();
//			userStaus.setIp(socket.getInetAddress().toString());
//			try {
//				userStaus.setOos(new ObjectOutputStream(socket.getOutputStream()));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			userStaus = new UserStaus();
//			userStaus.setIp(socket.getInetAddress().toString());
//			try {
//				userStaus.setOos(new ObjectOutputStream(socket.getOutputStream()));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		// System.out.println(socket.getInetAddress());
//		System.out.println("연결굳");
//
//		try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
//			ObjectMessage objectMessage;
//			String id = null;
//			while ((objectMessage = (ObjectMessage) ois.readObject()) != null) {
//				System.out.println("asdf");
//				newUserConnect(objectMessage);
//
//			}
//
//		} catch (Exception e) {
//			// 유저 커넥 끊김 캐치 부분
//			e.printStackTrace();
//			upDateUser();
//		}

//
//	private void newUserConnect(ObjectMessage objectMessage) {
//		// TODO Auto-generated method stub
//
//		if (objectMessage.getUserInfo() != null && usersStaus.size() > 0) {
//
//			for (UserStaus userStaus : usersStaus) {
//				if (userStaus.getIp().equals(socket.getInetAddress().toString())) {
//					// 이미 등록된 유저
//					System.out.println("기존 등록 유저");
//					pushMessageSC(1, 601, "데이터가 있는 유저입니다." + " 기존 데이터 사용", userStaus);
//				}
//
//			}
//
//			System.out.println("처음 온걸 환영합닏다.");
//			userStaus.setId(objectMessage.getUserInfo().getId());
//			userStaus.setPwd(objectMessage.getUserInfo().getPwd());
//			usersStaus.add(userStaus);
//			pushMessageSC(1, 600, "신규 유저입니다.", userStaus);
//
//		} else {
//			System.out.println("처음 온걸 환영합닏다1.");
//			userStaus.setId(objectMessage.getUserInfo().getId());
//			userStaus.setPwd(objectMessage.getUserInfo().getPwd());
//			usersStaus.add(userStaus);
//			pushMessageSC(1, 600, "신규 유저입니다.", userStaus);
//
//		}
//
//		upDateUser();
//	}
//
//	public void upDateUser() {
//		for (UserStaus userStaus : usersStaus) {
//			System.out.println("sdf");
//			if (userStaus.getOos() != null) {
//				String id = userStaus.getId();
//				String pwd = "테스트";// userStaus.getPwd();
//				System.out.println(id);
//				pushMessageUI(2, id, pwd, userStaus);
//				// 싱크로 나이즈드 체크 하기 여기서 에러나는거 같음
//
//				System.out.println("456");
//			}
//		}
//	}

	public void removeUser() {

	}

	public void pushMessageSC(int code, int returncode, String msg, UserStaus userStaus) {
		new Thread(new OutputThread(code, new StatusCode(returncode, msg), userStaus, mContext)).start();
	}

	public void pushMessageUI(int code, String id, String pwd, UserStaus userStaus) {
		new Thread(new OutputThread(code, new UserInfo(id, pwd), userStaus, mContext)).start();
	}

}
