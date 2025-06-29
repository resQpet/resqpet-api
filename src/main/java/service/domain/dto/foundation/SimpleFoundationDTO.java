package service.domain.dto.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.entity.foundation.Foundation;

import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleFoundationDTO extends BaseFoundationDTO {

    private List<FoundationLocationDTO> locations;

    public SimpleFoundationDTO(Foundation entity) {
        super(entity);
        if (Objects.nonNull(entity)) {
            this.locations = entity.getLocations().stream().map(FoundationLocationDTO::new).toList();
        }
    }
}