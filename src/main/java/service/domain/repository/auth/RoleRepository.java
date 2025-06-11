package service.domain.repository.auth;

import jakarta.validation.constraints.NotNull;
import service.domain.entity.auth.Role;
import service.domain.repository.BaseRepository;

public interface RoleRepository extends BaseRepository<Role> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

}
