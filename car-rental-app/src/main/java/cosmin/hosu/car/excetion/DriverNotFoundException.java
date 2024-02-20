package cosmin.hosu.car.excetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DriverNotFoundException extends RuntimeException {

	public DriverNotFoundException(String message) {
		super(message);
	}
}
