package Header;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectMessage implements Serializable {

	private static final long serialVersionUID = 2L;// 직렬화 버전 UID //L접미사 롱타입
	private UserInfo userInfo;
	private StatusCode statusCode;
	private int code1;

	public ObjectMessage(int code1, UserInfo userInfo) {
		this.code1 = code1;
		this.userInfo = userInfo;

	}

	public ObjectMessage(int code1, StatusCode statusCode) {
		this.code1 = code1;

		this.statusCode = statusCode;
	}

}
