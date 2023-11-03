package pl.app.common.security.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;

public class AuthenticationConfig {
    @Bean
    public AuthenticationService authenticationService(AuthenticationManager authenticationManager) {
        return new AuthenticationServiceImpl(authenticationManager);
    }
}
