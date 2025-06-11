package service.services.user.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.user.User;
import service.domain.entity.user.UserInfo;
import service.domain.exceptions.NotFoundException;
import service.domain.models.user.UserInfoRequest;
import service.domain.repository.user.UserInfoRepository;
import service.services.DefaultBaseService;

@Getter
@Service
@AllArgsConstructor
public class DefaultUserInfoService extends DefaultBaseService<UserInfo> implements UserInfoService {

    private final UserInfoRepository repository;

    /**
     * Creates a new UserInfo entry based on the provided User and UserPersonalRequest information.
     *
     * @param user     the User associated with the UserInfo
     * @param personal the personal information used to populate the UserInfo
     */
    @Override
    @Transactional
    public void create(User user, UserInfoRequest personal) {
        final UserInfo info = UserInfo.builder().user(user)
                .firstname(personal.getFirstname())
                .lastname(personal.getLastname())
                .gender(personal.getGender())
                .birth(personal.getBirthDate())
                .image(personal.getImage())
                .city(personal.getCity())
                .country(personal.getCountry())
                .build();
        user.setInfo(super.create(info));
    }

    /**
     * Updates the existing UserInfo entry associated with the provided User based on the provided UserPersonalRequest information.
     * If the UserInfo entry does not exist, a NotFoundException is thrown.
     *
     * @param user     the User associated with the UserInfo to be updated
     * @param personal the personal information used to update the UserInfo
     * @throws NotFoundException if the UserInfo entry for the provided User is not found
     */
    @Override
    @Transactional
    public void update(User user, UserInfoRequest personal) {
        final UserInfo info = repository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Información del usuario no encontrada"));

        info.setFirstname(personal.getFirstname());
        info.setLastname(personal.getLastname());
        info.setGender(personal.getGender());
        info.setBirth(personal.getBirthDate());
        info.setImage(personal.getImage());
        info.setCity(personal.getCity());
        info.setCountry(personal.getCountry());
        super.save(info);
    }

}
