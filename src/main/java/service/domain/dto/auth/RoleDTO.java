package service.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.dto.auth.authority.RoleAuthorityDTO;
import service.domain.entity.auth.Role;

import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO extends BaseDTO {

    private String name;
    private String description;
    private List<RoleAuthorityDTO> authorities;

    public RoleDTO(Role role) {
        super(role);
        if (Objects.nonNull(role)) {
            this.name = role.getName();
            this.description = role.getDescription();
            this.authorities = role.getAuthorities().stream().map(RoleAuthorityDTO::new).toList();
        }
    }

    public static RoleDTO of(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .description(role.getDescription())
                .build();
    }

}
