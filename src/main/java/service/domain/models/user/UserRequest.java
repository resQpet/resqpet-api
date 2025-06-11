package service.domain.models.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;

    private String document;

    @Email(message = "El correo electrónico no tiene un formato válido.")
    private String email;

    @NotNull(message = "El ID del rol es obligatorio.")
    private Long roleId;

    @Valid
    @NotNull(message = "La información personal del usuario es obligatoria.")
    private UserInfoRequest userInfo;

}
