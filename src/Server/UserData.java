package Server;

import java.io.ObjectOutputStream;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserData {

	private String name;
	private String[] target;
	private String message;
	private ObjectOutputStream oos;
}
