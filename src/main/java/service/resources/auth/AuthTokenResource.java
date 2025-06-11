package service.resources.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.aspect.Current;
import service.domain.dto.users.BaseUserDTO;
import service.domain.entity.user.User;
import service.domain.models.TokenInfo;
import service.services.auth.AuthService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "auth")
public class AuthTokenResource {

    private final AuthService authService;

    @RequestMapping(value = "token", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<TokenInfo> authToken(final Authentication authentication) {
        final TokenInfo authInfo = this.authService.authToken(authentication);
        return ResponseEntity.ok(authInfo);
    }

    @GetMapping("current")
    public ResponseEntity<BaseUserDTO> current(@Current User user) {
        return ResponseEntity.ok(new BaseUserDTO(user));
    }
}