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

	private ObjectInputStream ois;
	private Socket socket;
	private UserStaus userStaus;
	private Vector<UserStaus> usersStaus;

	public InputThread(Socket socket, Main mContext) {
		this.usersStaus = mContext.usersStatus;
		this.socket = socket;
		if (usersStaus.size() > 0) {
			for (UserStaus userStaus : usersStaus) {
				if (userStaus.getIp().equals(socket.getInetAddress().toString())) {
					this.userStaus = userStaus;
					try {
						userStaus.setOos(new ObjectOutputStream(socket.getOutputStream()));
						System.out.println(userStaus.getOos().toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			userStaus = new UserStaus();
			userStaus.setIp(socket.getInetAddress().toString());
			try {
				userStaus.setOos(new ObjectOutputStream(socket.getOutputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {

		// System.out.println(socket.getInetAddress());
		System.out.println("연결굳");

		try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
			ObjectMessage objectMessage;
			String id = null;
			while ((objectMessage = (ObjectMessage) ois.readObject()) != null) {
				System.out.println("asdf");
				newUserConnect(objectMessage);

			}

		} catch (Exception e) {
			// 유저 커넥 끊김 캐치 부분
			e.printStackTrace();
			upDateUser();
		}

	}

	private void newUserConnect(ObjectMessage objectMessage) {
		// TODO Auto-generated method stub

		if (objectMessage.getUserInfo() != null && usersStaus.size() > 0) {

			for (UserStaus userStaus : usersStaus) {
				if (userStaus.getIp().equals(socket.getInetAddress().toString())) {
					// 이미 등록된 유저
					System.out.println("기존 등록 유저");
					new Thread(new OutputThread(1, new StatusCode(601, "데이터가 있는 유저입니다." + " 기존 데이터 사용"),
							userStaus.getOos())).start();
				}

			}
		} else {
			System.out.println("처음 온걸 환영합닏다.");
			usersStaus.add(userStaus);
			new Thread(new OutputThread(1, new StatusCode(600, "신규 유저입니다."), userStaus.getOos())).start();
		}
		upDateUser();
	}

	public void upDateUser() {
		for (UserStaus userStaus : usersStaus) {
			System.out.println("sdf");
			if (userStaus.getOos() != null) {
				String id = userStaus.getId();
				String pwd = userStaus.getPwd();
				new Thread(new OutputThread(2, new UserInfo(id, pwd), this.userStaus.getOos())).start();
				System.out.println("456");
			}
		}
	}
}
