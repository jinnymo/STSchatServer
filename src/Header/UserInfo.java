package Header;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class UserInfo implements Serializable{
	
	private static final long serialVersionUID = 2L;//직렬화 버전 UID //L접미사 롱타입
	
	private String id;
	private String pwd;
	
	public UserInfo() {
		
	}


	public UserInfo(String id, String pwd) {
		this.id = id;
		this.pwd = pwd;
	}
}
