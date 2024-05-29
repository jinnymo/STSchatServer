package Header;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusCode implements Serializable{
	private static final long serialVersionUID = 2L;//직렬화 버전 UID //L접미사 롱타입
	private int code;
	private String statusMessage;
	
	public StatusCode(int code, String statusMessage) {
		this.code = code;
		this.statusMessage = statusMessage;
	}
	
	@Override
	public String toString() {
		return "code : "+code+" 메세지 : "+statusMessage;
	}
}
