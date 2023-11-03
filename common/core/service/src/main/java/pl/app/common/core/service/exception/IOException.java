package pl.app.common.core.service.exception;

public class IOException extends java.io.IOException {
    public IOException() {
        super("IOException");
    }
    public IOException(String message) {
        super(message);
    }

    public IOException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
