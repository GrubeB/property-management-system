package pl.app.property.accommodation_price.application.domain.exception;


import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface AccommodationTypePriceException {
    class NotFoundAccommodationTypePriceException extends NotFoundException {
        public NotFoundAccommodationTypePriceException() {
            super("not found accommodation type price");
        }

        public NotFoundAccommodationTypePriceException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypePriceException fromId(UUID id) {
            return new NotFoundAccommodationTypePriceException("not found accommodation type price with id: " + id);
        }
    }
}
