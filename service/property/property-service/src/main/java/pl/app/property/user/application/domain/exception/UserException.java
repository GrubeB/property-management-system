package pl.app.property.user.application.domain.exception;


import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface UserException {
    class NotFoundUserException extends NotFoundException {
        public NotFoundUserException() {
            super("not found user");
        }

        public NotFoundUserException(String message) {
            super(message);
        }

        public static NotFoundUserException fromId(UUID userId) {
            return new NotFoundUserException("not found user with id: " + userId);
        }

        public static NotFoundUserException fromEmail(String email) {
            return new NotFoundUserException("not found user with email: " + email);
        }
    }

    class NotFoundPermissionException extends NotFoundException {
        public NotFoundPermissionException() {
            super("not found permission");
        }

        public NotFoundPermissionException(String message) {
            super(message);
        }

        public static NotFoundPermissionException fromName(String name) {
            return new NotFoundPermissionException("not found permission with name: " + name);
        }
    }
}
