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
public class DriverDTO {
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	@NotBlank
	private String phone;

	private String extId;
}
