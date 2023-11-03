package pl.app.common.security.auditing;

import java.util.Optional;

public interface AuthenticationProvider {
    Optional<String> getCurrentUserName();
}
