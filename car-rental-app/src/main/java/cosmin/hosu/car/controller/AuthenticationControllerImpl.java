package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.AuthenticationRequest;
import cosmin.hosu.car.dto.AuthenticationResponse;
import cosmin.hosu.car.dto.RegisterRequest;
import cosmin.hosu.car.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationControllerImpl implements AuthenticationController {

	private final AuthenticationService authenticationService;

	@Override
	@PostMapping(path = "/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		return ok(authenticationService.register(request));
	}

	@Override
	@PostMapping(path = "/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
		return ok(authenticationService.login(request));
	}
}
