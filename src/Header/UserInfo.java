package Header;

import java.io.Serializable;

public class UserInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;//직렬화 버전 UID //L접미사 롱타입

	private String id;
	private String pwd;
	

	public UserInfo(String id , String pwd) {
		id = null;
		pwd = null;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Override
	public String toString() {
		
		return "id :"+id +"  pwd : "+pwd;
	}
}
