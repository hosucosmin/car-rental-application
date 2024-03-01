package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.CarDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
public interface CarController {

	@GetMapping
	List<CarDTO> getCars();

	@GetMapping("/{extId}")
	CarDTO getCarByExtId(@NotBlank @PathVariable("extId") String extId);

	@PostMapping(value = "/register")
	ResponseEntity<String> registerNewCar(@Valid @RequestBody CarDTO carDto);

	@PutMapping("/update")
	ResponseEntity<String> updateCar(@Valid @RequestBody CarDTO carDTO);

	@DeleteMapping("/delete/{extId}")
	ResponseEntity<String> deleteCar(@NotBlank @PathVariable("extId") String extId);
}
