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
public class CarDTO {

    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @NotBlank
    private String year;
    @NotBlank
    private String licensePlate;
    private int price;
    private String extId;
}
