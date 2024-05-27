package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import Header.ObjectMessage;
import Header.StatusCode;

public class InputThread implements Runnable {

	private ObjectInputStream ois;
	private Socket socket;
	private UserStaus userStaus;
	private Vector<UserStaus> usersStaus;

	public InputThread(Socket socket, Main mContext) {
		this.socket = socket;
		this.usersStaus = mContext.usersStatus;
		userStaus = new UserStaus();
		userStaus.setIp(socket.getInetAddress().toString());
		try {
			userStaus.setOos(new ObjectOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				if (objectMessage.getUserInfo() != null && usersStaus.size() >0 ) {
					
					for (UserStaus userStaus : usersStaus) {
						if (userStaus.getIp().equals(socket.getInetAddress().toString())) {
							// 이미 등록된 유저
							System.out.println("기존 등록 유저");
							new Thread(new OutputThread(new StatusCode(400, "접속 기록이 있는 유저입니다."), userStaus.getOos()))
									.start();
						} 

					}
				}  else {
					System.out.println("처음 온걸 환영합닏다.");
					usersStaus.add(userStaus);
				}

				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
