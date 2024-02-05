package cosmin.hosu.car.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RentRequestDTO {

	@NotBlank
	private String carLicensePlate;
	@NotBlank
	private String driverName;
	@NotBlank
	private String driverEmail;
	@NotBlank
	private String driverPhone;
}
