package service.domain.dto.auth.authority;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.entity.auth.RoleAuthority;

import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleAuthorityDTO extends BaseDTO {

    private AuthorityDTO authority;

    public RoleAuthorityDTO(RoleAuthority authority) {
        super(authority);
        if (Objects.nonNull(authority)) {
            this.authority = AuthorityDTO.builder()
                    .id(authority.getAuthority().getId())
                    .name(authority.getAuthority().getName())
                    .build();
        }
    }

}
