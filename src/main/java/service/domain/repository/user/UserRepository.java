package service.domain.repository.user;

import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import service.domain.entity.user.User;
import service.domain.repository.BaseRepository;
import service.domain.types.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT U FROM User U WHERE (U.username = :login OR U.document = :login OR U.email = :login)")
    List<User> findByLogin(@Param("login") String login, Pageable pageable);

    default Optional<User> findByLogin(String login) {
        return findByLogin(login, FIRST).stream().findFirst();
    }

    default Optional<User> findUser(@NonNull final String term) {
        return findByLogin(term);
    }

    int countByStatus(UserStatus status);

    Optional<User> findByDocument(String document);

    boolean existsByDocument(String document);

    boolean existsByDocumentAndIdNot(String document, Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
