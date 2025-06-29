package service.services.foundation.role;

import service.domain.entity.foundation.Foundation;
import service.domain.entity.foundation.FoundationUserRole;
import service.domain.entity.user.User;
import service.services.BaseService;

public interface FoundationUserRoleService extends BaseService<FoundationUserRole> {

    void create(Foundation foundation, User user);

}
