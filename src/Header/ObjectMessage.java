package Header;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectMessage implements Serializable {

	private  final long serialVersionUID = 1L;// 직렬화 버전 UID //L접미사 롱타입
	private  UserInfo userInfo;
	private  StatusCode statusCode;
	private int code1;

	public ObjectMessage() {
		userInfo = null;
		statusCode = null;
		code1 = 0;
	}

}
