package service.domain.request.foundation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import service.domain.types.foundation.EvidenceType;

@Data
public class FoundationEvidenceRequest {

    @NotNull(message = "El archivo es requerido")
    private String fileUrl;

    @NotNull(message = "El tipo de archivo es requerido")
    private EvidenceType fileType;

}