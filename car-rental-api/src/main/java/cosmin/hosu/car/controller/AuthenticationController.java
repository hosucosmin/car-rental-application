package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.AuthenticationRequest;
import cosmin.hosu.car.dto.AuthenticationResponse;
import cosmin.hosu.car.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
public interface AuthenticationController {

	@PostMapping(path = "/register")
	ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request);

	@PostMapping(path = "/login")
	ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request);
}
