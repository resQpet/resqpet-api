package service.config.security.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import service.domain.entity.user.User;
import service.domain.exceptions.ForbiddenException;
import service.domain.models.user.CustomUserDetails;
import service.services.user.UserService;

@Slf4j
@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final User user = this.userService.findByLogin(username);
            return new CustomUserDetails(user);
        } catch (Exception e) {
            log.warn("Unable to login: {}.", username);
            throw new ForbiddenException(e.getMessage());
        }
    }
}
