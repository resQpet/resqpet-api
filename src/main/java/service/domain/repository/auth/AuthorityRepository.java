package service.domain.repository.auth;

import service.domain.entity.auth.Authority;
import service.domain.repository.BaseRepository;

public interface AuthorityRepository extends BaseRepository<Authority> {

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByName(String name);

}
