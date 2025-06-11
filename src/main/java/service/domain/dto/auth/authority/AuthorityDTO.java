package service.domain.dto.auth.authority;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.entity.auth.Authority;

import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorityDTO extends BaseDTO {

    private String name;
    private String description;

    public AuthorityDTO(Authority entity) {
        super(entity);
        if (Objects.nonNull(entity)) {
            this.name = entity.getName();
            this.description = entity.getDescription();
        }
    }
}
