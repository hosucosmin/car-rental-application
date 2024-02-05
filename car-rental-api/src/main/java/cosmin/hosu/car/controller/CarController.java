package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.CarDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
public interface CarController {

	@GetMapping
	List<CarDTO> getCars();
	@PostMapping(value = "/register")
	ResponseEntity<String> registerNewCar(@Valid @RequestBody CarDTO carDto);

	@PutMapping("/update")
	ResponseEntity<String> updateCar(@Valid @RequestBody CarDTO carDTO);

	@DeleteMapping
	void deleteCar(@RequestBody CarDTO carDTO);
}
