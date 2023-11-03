package pl.app.property.guest.exception;


import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface GuestException {
    class NotFoundGuestException extends NotFoundException {
        public NotFoundGuestException() {
            super("not found guest");
        }

        public NotFoundGuestException(String message) {
            super(message);
        }

        public static NotFoundGuestException fromId(UUID guestId) {
            return new NotFoundGuestException("not found guest with id: " + guestId);
        }
    }

}
