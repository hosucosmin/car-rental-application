package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.DriverDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriverService {
	List<DriverDTO> getDrivers();

	ResponseEntity<String> addNewDriver(DriverDTO driverDTO);

	ResponseEntity<String> updateDriver(DriverDTO driverDTO);

	void deleteDriver(DriverDTO json);

	DriverDTO getDriverByExtId(String extId);
}
