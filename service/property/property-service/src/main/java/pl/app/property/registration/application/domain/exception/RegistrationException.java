package pl.app.property.registration.application.domain.exception;


import pl.app.common.core.service.exception.InvalidStateException;
import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface RegistrationException {
    class NotFoundRegistrationException extends NotFoundException {
        public NotFoundRegistrationException() {
            super("not found registration");
        }

        public NotFoundRegistrationException(String message) {
            super(message);
        }

        public static NotFoundRegistrationException fromId(UUID registrationId) {
            return new NotFoundRegistrationException("not found registration with id: " + registrationId);
        }
    }

    class NotFoundPartyException extends NotFoundException {
        public NotFoundPartyException() {
            super("not found party");
        }

        public NotFoundPartyException(String message) {
            super(message);
        }

        public static NotFoundPartyException fromId(UUID partyId) {
            return new NotFoundPartyException("not found party with id: " + partyId);
        }
    }

    class NotFoundAccommodationTypeBookingException extends NotFoundException {
        public NotFoundAccommodationTypeBookingException() {
            super("not found booking");
        }

        public NotFoundAccommodationTypeBookingException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypeBookingException fromId(UUID accommodationTypeBookingId) {
            return new NotFoundAccommodationTypeBookingException("not found booking with id: " + accommodationTypeBookingId);
        }
    }

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

    class RegistrationWrongStatedException extends InvalidStateException {
        public RegistrationWrongStatedException() {
            super("registration has wrong state");
        }

        public RegistrationWrongStatedException(String message) {
            super(message);
        }
    }
    class RegistrationFolioWrongStatedException extends InvalidStateException {
        public RegistrationFolioWrongStatedException() {
            super("registration folio has wrong state");
        }

        public RegistrationFolioWrongStatedException(String message) {
            super(message);
        }
    }
    class BookingWrongStatedException extends InvalidStateException {
        public BookingWrongStatedException() {
            super("booking has wrong state");
        }

        public BookingWrongStatedException(String message) {
            super(message);
        }
    }
}
