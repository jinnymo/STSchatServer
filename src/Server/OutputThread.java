package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import Header.ObjectMessage;
import Header.StatusCode;
import Header.UserInfo;

public class OutputThread implements Runnable{
	
	ObjectMessage objectMessage;
	ObjectOutputStream oos;
	public OutputThread(int code,StatusCode statusCode,ObjectOutputStream oos) {
		this.oos = oos;
		objectMessage = new ObjectMessage();
		objectMessage.setCode1(code);
		objectMessage.setStatusCode(statusCode);
		
	}
	public OutputThread(int code,UserInfo userInfo,ObjectOutputStream oos) {
		this.oos = oos;
		objectMessage = new ObjectMessage();
		objectMessage.setCode1(code);
		objectMessage.setUserInfo(userInfo);
	}
	@Override
	public void run() {
		
		try {
			oos.writeObject(objectMessage);
			System.out.println("데이터 전송 서버에서");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
