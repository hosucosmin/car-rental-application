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
public class RegisterRequest {

   @NotBlank
   private String firstname;
   @NotBlank
   private String lastname;
   @NotBlank
   private String email;
   @NotBlank
   private String password;
}
