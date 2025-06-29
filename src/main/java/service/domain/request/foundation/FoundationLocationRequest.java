package service.domain.request.foundation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FoundationLocationRequest {

    @Size(max = 100)
    @NotNull(message = "El pais es requerido")
    private String country;

    @Size(max = 100)
    @NotNull(message = "La ciudad es requerida")
    private String city;

    @Size(max = 255)
    @NotNull(message = "La direccion es requerida")
    private String address;

    @Size(max = 20)
    @NotNull(message = "El codigo postal es requerido")
    private String postalCode;

    @NotNull(message = "La latitud es requerida")
    private Float latitude;

    @NotNull(message = "La longitud es requerida")
    private Float longitude;

}