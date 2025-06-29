package service.domain.dto.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.entity.foundation.FoundationType;

import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoundationTypeDTO extends BaseDTO {

    private String name;
    private String description;

    public FoundationTypeDTO(FoundationType entity) {
        super(entity);
        if (Objects.nonNull(entity)) {
            this.name = entity.getName();
            this.description = entity.getDescription();
        }
    }
}