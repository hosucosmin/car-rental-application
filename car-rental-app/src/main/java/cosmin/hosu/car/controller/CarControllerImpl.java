package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/car")
public class CarControllerImpl implements CarController {

	private final CarServiceImpl carService;

	@Autowired
	public CarControllerImpl(CarServiceImpl carService) {
		this.carService = carService;
	}

	@GetMapping
	@Override
	public List<CarDTO> getCars() {
		return carService.getCars();
	}

	@PostMapping("/register")
	@Override
	public ResponseEntity<String> registerNewCar(@RequestBody CarDTO carDTO) {
		return carService.addNewCar(carDTO);
	}

	@PutMapping("/update")
	@Override
	public ResponseEntity<String> updateCar(@RequestBody CarDTO carDTO) {
		return carService.updateCar(carDTO);
	}

	@DeleteMapping
	@Override
	public void deleteCar(@RequestBody CarDTO carDTO) {
		carService.deleteCar(carDTO);
	}
}
