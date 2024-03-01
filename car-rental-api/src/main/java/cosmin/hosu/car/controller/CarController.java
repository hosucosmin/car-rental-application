package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.dto.LicensePlateChangeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

	@PatchMapping("/update/{extId}")
	ResponseEntity<String> updateCarLicensePlate(@Valid @RequestBody LicensePlateChangeRequest licensePlateChangeRequest,
	                                             @NotBlank @PathVariable("extId") String extId);

	@DeleteMapping("/delete/{extId}")
	ResponseEntity<String> deleteCar(@NotBlank @PathVariable("extId") String extId);
}
