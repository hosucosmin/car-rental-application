package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.dto.LicensePlateChangeRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarService {
	List<CarDTO> getCars();

	ResponseEntity<String> addNewCar(CarDTO carDTO);

	ResponseEntity<String> updateCar(CarDTO carDTO);

	ResponseEntity<String> deleteCar(String extId);

	CarDTO getCarByExtId(String extId);

	ResponseEntity<String> updateCarLicensePlate(LicensePlateChangeRequest licensePlateChangeRequest, String extId);
}
