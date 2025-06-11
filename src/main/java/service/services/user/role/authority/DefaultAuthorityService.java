package service.services.user.role.authority;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import service.domain.entity.auth.Authority;
import service.domain.exceptions.DuplicateException;
import service.domain.exceptions.NotFoundException;
import service.domain.models.authority.AuthorityRequest;
import service.domain.repository.auth.AuthorityRepository;
import service.services.DefaultBaseService;

@Getter
@Service
@AllArgsConstructor
public class DefaultAuthorityService extends DefaultBaseService<Authority> implements AuthorityService {

    private final AuthorityRepository repository;

    /**
     * Creates a new authority based on the provided request.
     *
     * @param request the AuthorityRequest object containing the authority details
     * @return the newly created Authority object
     * @throws DuplicateException if the authority name already exists
     */
    @Override
    @Transactional
    public Authority create(AuthorityRequest request) {
        validateIfNameExists(request);

        final Authority authority = Authority.builder()
                .name(request.name())
                .description(request.description())
                .build();
        return this.create(authority);
    }

    /**
     * Updates an existing authority with the given ID using the details provided in the request.
     *
     * @param id the ID of the authority to be updated
     * @param request the AuthorityRequest object containing the updated authority details
     * @return the updated Authority object
     * @throws DuplicateException if the authority name already exists
     * @throws NotFoundException if the authority with the specified ID is not found
     */
    @Override
    @Transactional
    public Authority update(Long id, AuthorityRequest request) {
        validateIfNameExists(id, request);
        final Authority authority = this.findById(id);

        authority.setName(request.name());
        authority.setDescription(request.description());
        return this.save(authority);
    }

    private void validateIfNameExists(Long id, AuthorityRequest request) {
        if (repository.existsByNameAndIdNot(request.name(), id)) {
            throw new DuplicateException("Authority name already exists");
        }
    }

    private void validateIfNameExists(AuthorityRequest request) {
        if (repository.existsByName(request.name())) {
            throw new DuplicateException("Authority name already exists");
        }
    }

}
