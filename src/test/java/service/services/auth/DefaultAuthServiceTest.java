package service.services.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import service.domain.entity.user.User;
import service.domain.models.TokenInfo;
import service.services.user.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DefaultAuthServiceTest {

    private static final String JWT_ISSUER = "TestIssuer";
    private static final long TOKEN_EXPIRES_YEAR = 60L * 60 * 24 * 365;

    @InjectMocks
    private DefaultAuthService defaultAuthService;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Test
    public void testAuthToken_GeneratesValidTokenForAuthenticatedUser() {
        // Arrange
        final Long userId = 1L;
        final User user = new User();
        user.setId(userId);

        String expectedTokenValue = "sample-jwt-token";

        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(userService.findByLogin("testUser")).thenReturn(user);

        Jwt jwtMock = Mockito.mock(Jwt.class);
        Mockito.when(jwtMock.getTokenValue()).thenReturn(expectedTokenValue);

        ArgumentCaptor<JwtEncoderParameters> captor = ArgumentCaptor.forClass(JwtEncoderParameters.class);
        Mockito.when(jwtEncoder.encode(captor.capture())).thenReturn(jwtMock);

        // Act
        TokenInfo tokenInfo = defaultAuthService.authToken(authentication);

        // Assert token info no es null y token coincide
        assertNotNull(tokenInfo);
        assertEquals(expectedTokenValue, tokenInfo.token());

        // Verificar claims pasados a encoder
        JwtClaimsSet actualClaims = captor.getValue().getClaims();

        assertEquals(userId.toString(), actualClaims.getSubject());
        assertNotNull(actualClaims.getIssuedAt());
        assertNotNull(actualClaims.getExpiresAt());
        assertTrue(actualClaims.getExpiresAt().isAfter(actualClaims.getIssuedAt()));
    }

    @Test
    public void testAuthToken_ThrowsExceptionWhenUserNotFound() {
        Mockito.when(authentication.getName()).thenReturn("nonExistingUser");
        Mockito.when(userService.findByLogin("nonExistingUser"))
                .thenThrow(new IllegalArgumentException("User not found"));

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            defaultAuthService.authToken(authentication);
        });

        assertEquals("User not found", ex.getMessage());
    }
}
