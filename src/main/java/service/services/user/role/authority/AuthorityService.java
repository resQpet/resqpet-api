package service.services.user.role.authority;

import service.domain.entity.auth.Authority;
import service.domain.models.authority.AuthorityRequest;
import service.services.BaseService;

public interface AuthorityService extends BaseService<Authority> {

    Authority create(AuthorityRequest request);

    Authority update(Long id, AuthorityRequest request);

}
