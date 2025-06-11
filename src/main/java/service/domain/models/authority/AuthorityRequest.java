package service.domain.models.authority;

import jakarta.validation.constraints.NotEmpty;

public record AuthorityRequest(@NotEmpty(message = "El name es requerido") String name,
                               @NotEmpty(message = "La descripcion es requerida") String description) {
}
