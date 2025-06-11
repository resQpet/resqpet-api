package service.services.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.auth.Role;
import service.domain.entity.user.User;
import service.domain.exceptions.DuplicateException;
import service.domain.exceptions.NotFoundException;
import service.domain.models.user.UserInfoRequest;
import service.domain.models.user.UserRequest;
import service.domain.repository.user.UserRepository;
import service.domain.types.UserStatus;
import service.services.DefaultBaseService;
import service.services.user.info.UserInfoService;
import service.services.user.role.RoleService;

@Slf4j
@Getter
@Service
@AllArgsConstructor
public class DefaultUserService extends DefaultBaseService<User> implements UserService {

    private final PasswordEncoder encoder;
    private final RoleService roleService;
    private final UserRepository repository;
    private final UserInfoService userInfoService;

    /**
     * Finds a user by their login username.
     *
     * @param username The login username of the user to search for
     * @return The user associated with the provided login username
     * @throws NotFoundException if no user is found with the given username
     */
    @Override
    public User findByLogin(String username) {
        return this.repository.findUser(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + username));
    }

    @Override
    public User findByDocument(String document) {
        return this.repository.findByDocument(document)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + document));
    }

    /**
     * Creates a new User entity based on the provided UserRequest.
     * This includes validating the uniqueness of username, email, and document,
     * creating the user, and associating relevant user information.
     *
     * @param request The UserRequest object containing the details necessary to create a new user,
     *                such as username, email, document, password, and personal information.
     * @return The created User entity after being persisted in the system.
     * @throws DuplicateException if the username, email, or document already exists.
     */
    @Override
    @Transactional
    public User create(UserRequest request) {
        final UserInfoRequest personal = request.getUserInfo();

        existsByUsername(request.getUsername());
        existsByEmail(request.getEmail());
        existsByDocument(request.getDocument());

        // Performs User Creation...
        final User user = this.handleCreateUser(request);
        this.userInfoService.create(user, personal);
        return user;
    }

    /**
     * Updates an existing User entity identified by the provided ID with the data from the given request.
     * This includes verifying the uniqueness of username, email, and document, and updating associated user information.
     *
     * @param id      The unique identifier of the user to be updated.
     * @param request The UserRequest object containing the updated user details,
     *                such as username, email, document, and personal information.
     * @return The updated User entity after the changes are applied and persisted.
     * @throws DuplicateException if the username, email, or document already exists for a different user.
     * @throws NotFoundException if the user with the specified ID does not exist.
     */
    @Override
    @Transactional
    public User update(Long id, UserRequest request) {
        final UserInfoRequest personal = request.getUserInfo();

        existsByUsername(id, request.getUsername());
        existsByEmail(id, request.getEmail());
        existsByDocument(request.getDocument(), id);

        final User user = this.handleUpdateUser(id, request);

        this.userInfoService.update(user, personal);

        return user;
    }

    /**
     * Updates the status of a User entity identified by the given ID.
     *
     * @param id     The unique identifier of the user whose status is to be updated.
     * @param status The new status to be applied to the user.
     * @return The updated User entity with the new status applied.
     */
    @Override
    @Transactional
    public User statusChange(Long id, UserStatus status) {
        final User updatedUser = this.findById(id);
        updatedUser.setStatus(status);
        return this.save(updatedUser);
    }

    private void existsByDocument(String document, Long id) {
        if (this.repository.existsByDocumentAndIdNot(document, id)) {
            throw new DuplicateException("El documento ya existe");
        }
    }

    private void existsByDocument(String document) {
        if (this.repository.existsByDocument(document)) {
            throw new DuplicateException("El document ya existe");
        }
    }

    private void existsByEmail(String email) {
        if (this.repository.existsByEmail(email)) {
            throw new DuplicateException("El correo electrónico ya existe");
        }
    }

    private void existsByEmail(Long id, String email) {
        if (this.repository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateException("El correo electrónico ya existe");
        }

    }

    private void existsByUsername(String username) {
        if (this.repository.existsByUsername(username)) {
            throw new DuplicateException("El username ya existe");
        }
    }

    private void existsByUsername(Long id, String username) {
        boolean exists = repository.existsByUsernameAndIdNot(username, id);
        if (exists) {
            throw new DuplicateException("El username ya existe");
        }
    }

    /**
     * Handles the creation of a new User entity based on the provided UserRequest.
     * This includes loading the role, encoding the password, and setting the user details.
     *
     * @param request The UserRequest object containing the details necessary to create a new user,
     *                including username, email, document, password, and role ID.
     * @return The created User entity with the provided details.
     */
    private User handleCreateUser(UserRequest request) {
        // Loads role for user
        final Role role = this.roleService.findById(request.getRoleId());
        // Generates a new password with a user document
        final String password = encoder.encode(request.getPassword());
        final User user = User.builder()
                .document(request.getDocument())
                .username(request.getUsername())
                .status(UserStatus.ACTIVE)
                .email(request.getEmail())
                .password(password)
                .role(role)
                .build();
        return super.create(user);
    }

    /**
     * Updates an existing User entity with the provided details from the UserRequest.
     * This includes updating attributes such as document, username, email,
     * and the associated role of the user.
     *
     * @param id      The unique identifier of the user to be updated.
     * @param request The UserRequest object containing the updated information
     *                for the user, including role ID, document, username, and email.
     * @return The updated User entity after applying the changes and saving to the system.
     */
    private User handleUpdateUser(Long id, UserRequest request) {
        final User user = this.findById(id);
        // Loads role for user
        final Role role = this.roleService.findById(request.getRoleId());

        user.setDocument(request.getDocument());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(role);

        return this.save(user);
    }
}
