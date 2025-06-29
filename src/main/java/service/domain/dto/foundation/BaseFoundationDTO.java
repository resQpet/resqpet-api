package service.domain.dto.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.entity.foundation.Foundation;
import service.domain.types.FoundationStatus;

import java.time.LocalDate;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseFoundationDTO extends BaseDTO {

    private String name;
    private LocalDate foundedDate;
    private String email;
    private String phone;
    private String website;
    private Integer memberCount;
    private FoundationStatus status;

    public BaseFoundationDTO(Foundation entity) {
        super(entity);
        if (Objects.nonNull(entity)) {
            this.name = entity.getName();
            this.foundedDate = entity.getFoundedDate();
            this.email = entity.getEmail();
            this.phone = entity.getPhone();
            this.website = entity.getWebsite();
            this.memberCount = entity.getMemberCount();
            this.status = entity.getStatus();
        }
    }
}