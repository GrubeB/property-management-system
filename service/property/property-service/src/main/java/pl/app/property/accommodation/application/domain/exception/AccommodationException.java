package pl.app.property.accommodation.application.domain.exception;


import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;


public interface AccommodationException {
    class NotFoundAccommodationException extends NotFoundException {
        public NotFoundAccommodationException() {
            super("not found accommodation");
        }

        public NotFoundAccommodationException(String message) {
            super(message);
        }

        public static NotFoundAccommodationException fromId(UUID accommodationId) {
            return new NotFoundAccommodationException("not found accommodation with id: " + accommodationId);
        }
    }
}
