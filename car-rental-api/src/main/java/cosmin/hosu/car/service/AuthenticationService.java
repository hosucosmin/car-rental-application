package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.AuthenticationRequest;
import cosmin.hosu.car.dto.AuthenticationResponse;
import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthenticationService {
	AuthenticationResponse register(RegisterRequest request);
	AuthenticationResponse login(AuthenticationRequest request);
}
