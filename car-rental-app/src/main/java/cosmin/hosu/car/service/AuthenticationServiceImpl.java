package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.AuthenticationRequest;
import cosmin.hosu.car.dto.AuthenticationResponse;
import cosmin.hosu.car.dto.RegisterRequest;
import cosmin.hosu.car.entities.Role;
import cosmin.hosu.car.entities.User;
import cosmin.hosu.car.excetion.UserNotRegisteredException;
import cosmin.hosu.car.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
			throw new UserNotRegisteredException("User already exists");
		});
		userRepository.save(user);

		var jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	@Override
	public AuthenticationResponse login(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
		);
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotRegisteredException("User not found. You need to register first."));

		var jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder().token(jwtToken).build();
	}
}
