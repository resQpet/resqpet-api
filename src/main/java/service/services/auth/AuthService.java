package service.services.auth;

import org.springframework.security.core.Authentication;
import service.domain.models.TokenInfo;

public interface AuthService {

    String JWT_ISSUER = "self";
    String COMPANY_CLAIM = "org";
    Long TOKEN_EXPIRES_24_HOURS = 3600 * 24L;
    Long TOKEN_EXPIRES_YEAR = 3600 * 24L * 365;

    TokenInfo authToken(Authentication authentication);

}
