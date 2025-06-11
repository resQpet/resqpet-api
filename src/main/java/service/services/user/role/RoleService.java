package service.services.user.role;

import service.domain.entity.auth.Role;
import service.domain.models.role.RoleRequest;
import service.services.BaseService;

public interface RoleService extends BaseService<Role> {

    Role create(RoleRequest request);

    Role update(Long id, RoleRequest role);

    Role assign(Long roleId, Long authorityId);

}
