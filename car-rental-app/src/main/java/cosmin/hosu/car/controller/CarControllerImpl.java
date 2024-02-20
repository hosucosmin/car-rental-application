package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.service.CarServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/car")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CarControllerImpl implements CarController {

	private final CarServiceImpl carService;

	@GetMapping
	@Override
	public List<CarDTO> getCars() {
		return carService.getCars();
	}

	@GetMapping("/{extId}")
	@Override
	@Cacheable(value = "car_cache")
	public CarDTO getCarByExtId(@PathVariable("extId") String extId) {
		return carService.getCarByExtId(extId);
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
