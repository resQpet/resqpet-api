package service.domain.dto.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.dto.auth.RoleDTO;
import service.domain.entity.user.User;
import service.domain.types.UserStatus;

import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseUserDTO extends BaseDTO {

    private RoleDTO role;
    private String email;
    private String username;
    private String document;
    private UserStatus status;
    private UserInfoDTO info;

    public BaseUserDTO(User user) {
        super(user);
        if (Objects.nonNull(user)) {
            this.role = new RoleDTO(user.getRole());
            this.email = user.getEmail();
            this.username = user.getUsername();
            this.document = user.getDocument();
            this.status = user.getStatus();
            this.info = new UserInfoDTO(user.getInfo());
        }
    }
}
