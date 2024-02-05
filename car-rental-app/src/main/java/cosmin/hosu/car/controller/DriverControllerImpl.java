package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.DriverDTO;
import cosmin.hosu.car.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/driver")
public class DriverControllerImpl implements DriverController {

	private final DriverService driverService;

	@Autowired
	public DriverControllerImpl(DriverService driverService) {
		this.driverService = driverService;
	}

	@GetMapping
	@Override
	public List<DriverDTO> getDrivers() {
		return driverService.getDrivers();
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
