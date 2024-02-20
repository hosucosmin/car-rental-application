package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.CarDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarService {
	List<CarDTO> getCars();

	ResponseEntity<String> addNewCar(CarDTO carDTO);

	ResponseEntity<String> updateCar(CarDTO carDTO);

	void deleteCar(CarDTO carDTO);

	CarDTO getCarByExtId(String extId);
}
