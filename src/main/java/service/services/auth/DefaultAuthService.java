package service.services.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import service.domain.entity.user.User;
import service.domain.models.TokenInfo;
import service.services.user.UserService;

import java.time.Instant;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultAuthService implements AuthService {

    private final JwtEncoder encoder;
    private final UserService userService;

    @Override
    public TokenInfo authToken(Authentication authentication) {
        // Loads user info
        final User user = this.userService.findByLogin(authentication.getName());
        return getTokenInfo(user);
    }

    private TokenInfo getTokenInfo(User user) {
        final Instant issuedAt = Instant.now();
        final Instant expiresAt = issuedAt.plusSeconds(TOKEN_EXPIRES_YEAR);

        final JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(JWT_ISSUER)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(user.getId().toString())
                .build();

        final String token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new TokenInfo(token);
    }


}
