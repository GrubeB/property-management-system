package pl.app.common.security.token;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface TokenService {
    String generateToken(Authentication authentication);

    String generateToken(Authentication authentication, Map<String, Object> extraClaims);

    boolean isTokenValid(String token);

    Authentication extractAuthentication(String token);

    String resolveBearerTokenFromRequest(HttpServletRequest request);
}
