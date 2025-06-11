package service.services.user.info;

import service.domain.entity.user.User;
import service.domain.entity.user.UserInfo;
import service.domain.models.user.UserInfoRequest;
import service.services.BaseService;

public interface UserInfoService extends BaseService<UserInfo> {

    void create(User user, UserInfoRequest personal);

    void update(User user, UserInfoRequest personal);

}
