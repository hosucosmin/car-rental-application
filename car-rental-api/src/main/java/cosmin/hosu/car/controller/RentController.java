package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.RentDTO;
import cosmin.hosu.car.dto.RentRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
public interface RentController {
	@GetMapping
	List<RentDTO> getCurrentRents();

	@GetMapping("/{extId}")
	RentDTO getRentByExtId(@NotBlank @PathVariable("extId") String extId);

	@GetMapping("/finished")
	List<RentDTO> getFinishedRents();

	@PostMapping("/register")
	ResponseEntity<String> registerNewRent(@Valid @RequestBody RentRequestDTO rentRequestDTO);

	@PutMapping("/end-rent")
	ResponseEntity<String> endRent(@RequestBody Map<String, String> json);

	@DeleteMapping
	void deleteRent(@RequestBody Map<String, String> json);
}
