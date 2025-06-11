package service.domain.repository.auth;

import service.domain.entity.auth.AccountRecovery;
import service.domain.repository.BaseRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AccountRecoveryRepository extends BaseRepository<AccountRecovery> {

    default boolean isTokenValid(String token) {
        return existsByTokenAndExpiresAtGreaterThanAndConsumedFalse(token, LocalDateTime.now());
    }

    Optional<AccountRecovery> findByTokenAndExpiresAtGreaterThanAndConsumedFalse(String token, LocalDateTime expiresAt);

    boolean existsByTokenAndExpiresAtGreaterThanAndConsumedFalse(String token, LocalDateTime expiresAt);


    default Optional<AccountRecovery> findByTokenValid(String token) {
        return findByTokenAndExpiresAtGreaterThanAndConsumedFalse(token, LocalDateTime.now());
    }

}
