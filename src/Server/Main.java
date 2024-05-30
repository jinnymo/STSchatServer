package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Main {
	private static final int PORT = 5000;
	static Vector<UserData> userDatas = new Vector<>();
	
	
	public static void main(String[] args) {
		Main MainService = new Main();
		
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + " 접속");
				new Thread(new InputThread(socket, MainService,userDatas)).start();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
