package service.domain.models.account;

import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

public record PasswordRecoverRequest(@NotEmpty(message = "La nueva contraseña es requerida.") String password,
                                     @NotEmpty(message = "La confirmación de contraseña es requerida.") String repeated,
                                     @NotEmpty(message = "El token requerido.") String token) {
    public boolean isValid() {
        return Objects.nonNull(password) && password.equals(repeated);
    }
}
