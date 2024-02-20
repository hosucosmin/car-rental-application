package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.DriverDTO;
import cosmin.hosu.car.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/driver")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DriverControllerImpl implements DriverController {

	private final DriverService driverService;

	@GetMapping
	@Override
	public List<DriverDTO> getDrivers() {
		return driverService.getDrivers();
	}

	@GetMapping("/{extId}")
	@Override
	@Cacheable(value = "driver_cache")
	public DriverDTO getDriverByExtId(@PathVariable("extId") String extId) {
		return driverService.getDriverByExtId(extId);
	}

	@PostMapping("/register")
	@Override
	public ResponseEntity<String> registerDriver(@RequestBody DriverDTO driverDTO) {
		return driverService.addNewDriver(driverDTO);
	}

	@PutMapping("/update")
	@Override
	public ResponseEntity<String> updateDriver(@RequestBody DriverDTO driverDTO) {
		return driverService.updateDriver(driverDTO);
	}

	@DeleteMapping
	@Override
	public void deleteDriver(@RequestBody DriverDTO driverDTO) {
		driverService.deleteDriver(driverDTO);
	}

}
