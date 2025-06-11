package service.domain.models.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import service.domain.types.Gender;

import java.time.LocalDate;

@Data
public class UserInfoRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    private String firstname;

    @NotBlank(message = "El apellido es obligatorio.")
    private String lastname;

    @NotNull(message = "El género es obligatorio (MALE, FEMALE, OTHER).")
    private Gender gender;

    @NotBlank(message = "El país es obligatorio.")
    private String country;

    @NotBlank(message = "La ciudad es obligatoria.")
    private String city;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate birthDate;

    private String image;

}
