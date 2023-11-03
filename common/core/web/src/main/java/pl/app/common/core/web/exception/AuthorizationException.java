package pl.app.common.core.web.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("you have no access to perform the operation");
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
