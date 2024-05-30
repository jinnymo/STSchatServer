package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import Header.ObjectMessage;
import Header.StatusCode;
import Header.UserInfo;

public class OutputThread implements Runnable{
	
	ObjectMessage objectMessage;
	ObjectOutputStream oos;
	UserStaus userStaus;
	Main mContext;

	public OutputThread(int code1,UserInfo userInfo,UserStaus user,Main mContext) {
		this.oos = user.getOos();
		this.userStaus = user;
		this.mContext = mContext;
		//objectMessage = new ObjectMessage(code1,userInfo);
		
	}
	public OutputThread(int code1,StatusCode statusCode,UserStaus user,Main mContext) {
		this.oos = user.getOos();
		this.userStaus = user;
		this.mContext = mContext;
		//objectMessage = new ObjectMessage(code1, statusCode);
	}
	@Override
	public void run() {
		
		try {
			oos.writeObject(objectMessage);
			oos.flush();
			System.out.println("데이터 전송 서버에서");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//mContext.usersStatus.remove(userStaus);
		}
		
	}

}
