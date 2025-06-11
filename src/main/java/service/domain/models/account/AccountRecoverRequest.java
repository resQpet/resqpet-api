package service.domain.models.account;

import jakarta.validation.constraints.NotEmpty;

public record AccountRecoverRequest(@NotEmpty(message = "El nombre de usuario es requerido.") String username) {
}
