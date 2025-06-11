package service.services.user;

import service.domain.entity.user.User;
import service.domain.models.user.UserRequest;
import service.domain.types.UserStatus;
import service.services.BaseService;

public interface UserService extends BaseService<User> {

    User findByLogin(String username);

    User findByDocument(String document);

    User create(UserRequest request);

    User update(Long id, UserRequest request);

    User statusChange(Long id, UserStatus status);

}
