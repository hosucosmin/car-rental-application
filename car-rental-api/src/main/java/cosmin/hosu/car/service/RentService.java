package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.RentDTO;
import cosmin.hosu.car.dto.RentRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface RentService {
	ResponseEntity<String> registerNewEntry(RentRequestDTO rentRequestDTO);

	ResponseEntity<String> finishARide(Map<String, String> json);

	void deleteARent(Map<String, String> json);

	List<RentDTO> getCurrentRents();

	List<RentDTO> getFinishedRents();

	RentDTO getRentByExtId(String extId);
}
