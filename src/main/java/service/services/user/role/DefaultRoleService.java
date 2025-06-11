package service.services.user.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.auth.Authority;
import service.domain.entity.auth.Role;
import service.domain.entity.auth.RoleAuthority;
import service.domain.exceptions.DuplicateException;
import service.domain.models.role.RoleRequest;
import service.domain.repository.auth.RoleRepository;
import service.services.DefaultBaseService;
import service.services.user.role.assign.RoleAuthorityService;
import service.services.user.role.authority.AuthorityService;

@Getter
@Service
@AllArgsConstructor
public class DefaultRoleService extends DefaultBaseService<Role> implements RoleService {

    private final RoleRepository repository;
    private final RoleAuthorityService roleAuthorityService;
    private final AuthorityService authorityService;

    /**
     * Creates a new role based on the provided request.
     *
     * @param request The request containing the necessary information to create a new role.
     * @return The newly created role.
     * @throws DuplicateException If a role with the same name already exists.
     */
    @Override
    @Transactional
    public Role create(RoleRequest request) {
        validateIfRoleExists(request);

        final Role role = Role.builder()
                .name(request.name())
                .description(request.description())
                .build();
        return create(role);
    }

    /**
     * Updates an existing role based on the provided request.
     *
     * @param id The unique identifier of the role to be updated.
     * @param request The request containing the necessary information to update the role.
     * @return The updated role.
     * @throws DuplicateException If a role with the same name (excluding the current role) already exists.
     */
    @Override
    @Transactional
    public Role update(Long id, RoleRequest request) {
        final Role role = this.findById(id);
        validateRoleNameExcludingId(id, request);

        role.setName(request.name());
        role.setDescription(request.description());
        return save(role);
    }

    /**
     * Assigns an authority to a role by linking them through a RoleAuthority entity.
     *
     * @param roleId      The unique identifier of the role to which the authority will be assigned.
     * @param authorityId The unique identifier of the authority to be assigned to the role.
     * @return The role after the authority has been successfully assigned.
     */
    @Override
    @Transactional
    public Role assign(Long roleId, Long authorityId) {
        final Role role = this.findById(roleId);
        final Authority authority = this.authorityService.findById(authorityId);

        final RoleAuthority roleAuthority = RoleAuthority.builder()
                .authority(authority)
                .role(role)
                .build();

        this.roleAuthorityService.save(roleAuthority);
        return role;
    }

    /**
     * Validates if a role name already exists within the same organization, excluding a specific role by its ID.
     * Throws a DuplicateException if a duplicate role name is found.
     *
     * @param id      The unique identifier of the role to be excluded from the validation.
     * @param request The UpdateRoleRequest containing the name of the role to be validated.
     * @throws DuplicateException If the role name already exists in the given organization, excluding the specified role ID.
     */
    private void validateRoleNameExcludingId(Long id, RoleRequest request) {
        if (this.repository.existsByNameAndIdNot(request.name(), id)) {
            throw new DuplicateException("El nombre del rol ya existe");
        }
    }

    /**
     * Validates if a role with the specified name already exists within the given organization.
     * Throws a DuplicateException if a duplicate role is found.
     *
     * @param request The CreateRoleRequest containing the name of the role to be validated.
     * @throws DuplicateException If a role with the specified name already exists in the given organization.
     */
    private void validateIfRoleExists(RoleRequest request) {
        if (this.repository.existsByName(request.name())) {
            throw new DuplicateException("El rol ya existe");
        }
    }

}
