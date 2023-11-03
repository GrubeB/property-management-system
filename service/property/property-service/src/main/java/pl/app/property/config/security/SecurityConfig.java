package pl.app.property.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.app.common.core.model.config.HibernateListenerConfig;
import pl.app.common.core.model.config.RootAwareConfig;
import pl.app.common.security.auditing.AuditorConfig;
import pl.app.common.security.authentication.AuthenticationConfig;
import pl.app.common.security.authorization.AuthorizationConfig;
import pl.app.common.security.authorozation_method_security.MethodSecurityConfig;
import pl.app.common.security.token.TokenConfig;

@Configuration
@RequiredArgsConstructor
@Import({
        TokenConfig.class,
        AuthorizationConfig.class,
        AuthenticationConfig.class,
        AuditorConfig.class,
        RootAwareConfig.class,
        HibernateListenerConfig.class,
        MethodSecurityConfig.class
})
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
