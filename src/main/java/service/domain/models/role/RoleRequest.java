package service.domain.models.role;

import jakarta.validation.constraints.NotNull;

public record RoleRequest(@NotNull(message = "El nombre es requerido") String name,
                          @NotNull(message = "La descripción es requerida") String description) {
}
