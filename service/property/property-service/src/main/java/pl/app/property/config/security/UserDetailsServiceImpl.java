package pl.app.property.config.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.app.common.security.authorozation_method_security.DomainObjectGrandAuthority;
import pl.app.common.security.user_details.CustomUserDetails;
import pl.app.property.user.application.domain.model.Privilege;
import pl.app.property.user.application.domain.model.User;
import pl.app.property.user.application.port.in.FetchUserCommand;
import pl.app.property.user.application.port.in.FetchUserUseCase;

import java.util.List;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final FetchUserUseCase fetchUserUseCase;

    public UserDetailsServiceImpl(@Lazy FetchUserUseCase fetchUserUseCase) {
        this.fetchUserUseCase = fetchUserUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = fetchUserUseCase.fetchUser(new FetchUserCommand(email));
        return new CustomUserDetails(user.getUserId().toString(), user.getEmail(), user.getPassword(), toGrantedAuthority(user.getPrivileges()));
    }

    private static List<? extends GrantedAuthority> toGrantedAuthority(List<Privilege> privileges) {
        return privileges.stream()
                .map(p -> new DomainObjectGrandAuthority(
                        p.getDomainObjectId() != null ? p.getDomainObjectId().toString() : "",
                        p.getPermission().getPermissionLevel().name(),
                        p.getPermission().getName().name())
                ).toList();
    }
}
