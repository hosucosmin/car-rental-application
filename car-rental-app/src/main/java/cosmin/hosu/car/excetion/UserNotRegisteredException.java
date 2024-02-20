package cosmin.hosu.car.excetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotRegisteredException extends RuntimeException {

	public UserNotRegisteredException(String message) {
		super(message);
	}
}
