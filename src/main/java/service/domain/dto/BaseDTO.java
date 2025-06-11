package service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.entity.BaseEntity;

import java.util.Date;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {

    private Long id;
    private Date createdAt;
    private Date updatedAt;

    public BaseDTO(BaseEntity entity) {
        if (Objects.nonNull(entity)) {
            this.id = entity.getId();
            this.createdAt = entity.getCreatedAt();
            this.updatedAt = entity.getUpdatedAt();
        }
    }

}
