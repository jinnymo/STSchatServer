package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import Header.ObjectMessage;
import Header.StatusCode;

public class OutputThread implements Runnable{
	
	ObjectMessage objectMessage = new ObjectMessage();
	StatusCode statusCode;
	ObjectOutputStream oos;
	public OutputThread(StatusCode statusCode,ObjectOutputStream oos) {
		this.statusCode = statusCode;
		this.oos = oos;
	}
	@Override
	public void run() {
		objectMessage.setStatusCode(statusCode);
		try {
			oos.writeObject(objectMessage);
			System.out.println("데이터 전송 서버에서");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
