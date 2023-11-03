package pl.app.common.security.authorization;

public class AuthorizationServiceProvider {
    private static AuthorizationService authorizationServiceInstance;

    AuthorizationServiceProvider(AuthorizationService authorizationService) {
        authorizationServiceInstance = authorizationService;
    }

    public static AuthorizationService getAuthorizationInstance() {
        return authorizationServiceInstance;
    }
}
