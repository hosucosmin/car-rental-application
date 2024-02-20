package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.DriverDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
public interface DriverController {

	@GetMapping
	List<DriverDTO> getDrivers();

	@GetMapping("/{extId}")
	DriverDTO getDriverByExtId(@NotBlank @PathVariable("extId") String extId);

	@PostMapping(value = "/register")
	ResponseEntity<String> registerDriver(@Valid @RequestBody DriverDTO driverDTO);

	@PutMapping("/update")
	ResponseEntity<String> updateDriver(@Valid @RequestBody DriverDTO driverDTO);

	@DeleteMapping
	void deleteDriver(@RequestBody DriverDTO driverDTO);
}
