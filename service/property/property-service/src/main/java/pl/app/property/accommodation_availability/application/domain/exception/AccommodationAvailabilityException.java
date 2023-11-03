package pl.app.property.accommodation_availability.application.domain.exception;


import pl.app.common.core.service.exception.NotFoundException;
import pl.app.common.core.service.exception.ValidationException;

import java.util.UUID;

public interface AccommodationAvailabilityException {
    class NoAccommodationAvailableException extends RuntimeException {
        public NoAccommodationAvailableException() {
            super("no accommodation available");
        }

        public NoAccommodationAvailableException(String message) {
            super(message);
        }

        public static NoAccommodationAvailableException fromId(UUID id) {
            return new NoAccommodationAvailableException("accommodation is no available with id: " + id);
        }
    }

    class AccommodationReservationValidationException extends ValidationException {
        public AccommodationReservationValidationException() {
            super("argument is not valid");
        }

        public AccommodationReservationValidationException(String message) {
            super(message);
        }
    }

    class NotFoundAccommodationTypeReservationException extends NotFoundException {
        public NotFoundAccommodationTypeReservationException() {
            super("not found accommodation type reservation");
        }

        public NotFoundAccommodationTypeReservationException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypeReservationException fromId(UUID accommodationTypeReservationId) {
            return new NotFoundAccommodationTypeReservationException("not found accommodation type reservation with id: " + accommodationTypeReservationId);
        }
    }

    class NotFoundAccommodationReservationException extends NotFoundException {
        public NotFoundAccommodationReservationException() {
            super("not found accommodation reservation");
        }

        public NotFoundAccommodationReservationException(String message) {
            super(message);
        }

        public static NotFoundAccommodationReservationException fromId(UUID accommodationReservationId) {
            return new NotFoundAccommodationReservationException("not found accommodation reservation with id: " + accommodationReservationId);
        }
    }

    class NotFoundAccommodationTypeAvailabilityException extends NotFoundException {
        public NotFoundAccommodationTypeAvailabilityException() {
            super("not found accommodation type availability");
        }

        public NotFoundAccommodationTypeAvailabilityException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypeAvailabilityException fromId(UUID accommodationReservationId) {
            return new NotFoundAccommodationTypeAvailabilityException("not found  accommodation type availability with id: " + accommodationReservationId);
        }
    }
}
