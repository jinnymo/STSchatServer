package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import Header.ObjectMessage;
import Header.StatusCode;
import Header.UserInfo;

public class OutputThread implements Runnable{
	
	ObjectMessage objectMessage;
	ObjectOutputStream oos;

	public OutputThread(int code1,UserInfo userInfo,ObjectOutputStream oos) {
		this.oos = oos;
		objectMessage = new ObjectMessage(code1,userInfo);
	}
	public OutputThread(int code1,StatusCode statusCode,ObjectOutputStream oos) {
		this.oos = oos;
		objectMessage = new ObjectMessage(code1, statusCode);
	}
	@Override
	public void run() {
		
		try {
			oos.writeObject(objectMessage);
			oos.flush();
			System.out.println("데이터 전송 서버에서");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
