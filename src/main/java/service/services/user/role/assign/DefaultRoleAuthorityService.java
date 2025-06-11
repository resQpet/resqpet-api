package service.services.user.role.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import service.domain.entity.auth.RoleAuthority;
import service.domain.repository.auth.RoleAuthorityRepository;
import service.services.DefaultBaseService;

@Getter
@Service
@AllArgsConstructor
public class DefaultRoleAuthorityService extends DefaultBaseService<RoleAuthority> implements RoleAuthorityService {

    private final RoleAuthorityRepository repository;

}
