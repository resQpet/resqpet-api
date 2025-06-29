package service.domain.request.foundation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FoundationRequest {

    @NotNull(message = "El nombre es requerido")
    private String name;

    @NotNull(message = "La fecha es requerido")
    private LocalDate foundedDate;

    @NotNull(message = "El correo es requerido")
    private String email;

    @NotNull(message = "El telefono es requerido")
    private String phone;

    @NotNull(message = "La direccion es requerido")
    private String website;

    @NotNull(message = "Tiene que haber al menos un tipo de fundación")
    private List<Long> foundationTypeIds;

    @Valid
    @NotNull(message = "Tiene que haber al menos una ubicación")
    private List<FoundationLocationRequest> location;

    @Valid
    @NotNull(message = "Tiene que una evidencia")
    private FoundationEvidenceRequest evidence;

}
