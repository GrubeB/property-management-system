package pl.app.common.security.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import pl.app.common.security.authentication.AuthenticationService;

public class AuthorizationConfig {
    @Bean
    public AuthorizationService authorizationService(AuthenticationService authenticationService) {
        return new AuthorizationServiceImpl(authenticationService);
    }

    @Bean
    public AuthorizationServiceProvider authorizationServiceProvider(AuthorizationService authorizationService) {
        return new AuthorizationServiceProvider(authorizationService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
