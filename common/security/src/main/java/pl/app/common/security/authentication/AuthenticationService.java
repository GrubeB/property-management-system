package pl.app.common.security.authentication;

import org.springframework.security.core.Authentication;
import pl.app.common.security.auditing.AuthenticationProvider;

import java.util.Optional;

public interface AuthenticationService extends AuthenticationProvider {
    void setCurrentAuthentication(Authentication authentication);

    Authentication authenticate(String email, String password);

    Authentication getCurrentAuthentication();

    Optional<String> getCurrentUserName();

    Optional<String> getCurrentUserId();

    Optional<String> getAccessToken();
}
