package service.services.foundation.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.foundation.Foundation;
import service.domain.entity.foundation.FoundationUserRole;
import service.domain.entity.user.User;
import service.domain.repository.foundation.FoundationUserRoleRepository;
import service.domain.types.foundation.FoundationRoleType;
import service.services.DefaultBaseService;

import java.time.LocalDateTime;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationUserRoleService extends DefaultBaseService<FoundationUserRole> implements FoundationUserRoleService {

    private final FoundationUserRoleRepository repository;

    /**
     * Creates a new association between a given foundation and user with an admin role.
     *
     * @param foundation the foundation entity to associate with the user, must not be null
     * @param user the user entity to associate with the foundation, must not be null
     */
    @Override
    @Transactional
    public void create(Foundation foundation, User user) {
        final FoundationUserRole foundationUserRole = FoundationUserRole.builder()
                .foundation(foundation)
                .user(user)
                .role(FoundationRoleType.ADMIN)
                .assignedAt(LocalDateTime.now())
                .build();
        this.create(foundationUserRole);
    }
}
