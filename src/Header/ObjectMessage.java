package Header;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectMessage implements Serializable{

	private static final long serialVersionUID = 1L;//직렬화 버전 UID //L접미사 롱타입
	private UserInfo userInfo;
	private StatusCode statusCode;
	
	public ObjectMessage() {
		userInfo = null;
		statusCode = null;
	}

	
	


	
}
