package Header;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ObjectMessage implements Serializable {

	private static final long serialVersionUID = 3L;// 직렬화 버전 UID //L접미사 롱타입
	private String name;
	private String[] target;
	private String message;

	public ObjectMessage(String name, String[] target, String message) {
		this.name = name;
		this.target = target;
		this.message = message;

	}
	public ObjectMessage(String name) {
		this.name = name;
	}
	public ObjectMessage(String[] users) {
		this.target = users;
	}


}
