package service.domain.repository.user;

import service.domain.entity.user.User;
import service.domain.entity.user.UserInfo;
import service.domain.repository.BaseRepository;

import java.util.Optional;

public interface UserInfoRepository extends BaseRepository<UserInfo> {

    Optional<UserInfo> findByUser(User user);

}
