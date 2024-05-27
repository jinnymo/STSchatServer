package Server;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStaus {

	private String ip;
	private ObjectOutputStream oos;
	private String id;
	private String pwd;
	private String uStatus;

	public UserStaus() {
	
		ip = null;
		oos = null;
		id = null;
		pwd = null;
		uStatus = null;
				

	}

}
