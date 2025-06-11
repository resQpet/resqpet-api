package service.domain.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import service.domain.entity.auth.RoleAuthority;
import service.domain.entity.user.User;
import service.domain.entity.user.UserInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomUserDetails extends User implements UserDetails {

    public CustomUserDetails(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<RoleAuthority> authorities = super.getRole().getAuthorities();
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }

    @JsonIgnore
    public String getName() {
        return Optional.ofNullable(super.getInfo()).map(UserInfo::getName).orElse(null);
    }
}
