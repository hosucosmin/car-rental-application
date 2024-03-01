package cosmin.hosu.car.controller;

import cosmin.hosu.car.dto.RentDTO;
import cosmin.hosu.car.dto.RentRequestDTO;
import cosmin.hosu.car.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/rents")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentControllerImpl implements RentController {
	private final RentService rentService;

	@GetMapping
	@Override
	public List<RentDTO> getCurrentRents() {
		return rentService.getCurrentRents();
	}

	@GetMapping("/{extId}")
	@Override
	@Cacheable(value = "rent_cache")
	public RentDTO getRentByExtId(@PathVariable("extId") String extId) {
		return rentService.getRentByExtId(extId);
	}

	@GetMapping("/finished")
	@Override
	public List<RentDTO> getFinishedRents() {
		return rentService.getFinishedRents();
	}

	@PostMapping("/register")
	@Override
	public ResponseEntity<String> registerNewRent(@RequestBody RentRequestDTO rentRequestDTO) {
		return rentService.registerNewEntry(rentRequestDTO);
	}

	@PutMapping("/end-rent")
	@Override
	public ResponseEntity<String> endRent(@RequestBody Map<String, String> json) {
		return rentService.endRent(json);
	}

	@DeleteMapping
	@Override
	public void deleteRent(@RequestBody Map <String, String> json) {
		rentService.deleteARent(json);
	}

}
