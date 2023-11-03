package pl.app.property.reservation.application.domain.exception;


import pl.app.common.core.service.exception.InvalidStateException;
import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface ReservationException {
    class NotFoundReservationException extends NotFoundException {
        public NotFoundReservationException() {
            super("not found reservation");
        }

        public NotFoundReservationException(String message) {
            super(message);
        }

        public static NotFoundReservationException fromId(UUID reservationId) {
            return new NotFoundReservationException("not found reservation with id: " + reservationId);
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

    class ReservationWrongStatedException extends InvalidStateException {
        public ReservationWrongStatedException() {
            super("reservation has wrong stated");
        }

        public ReservationWrongStatedException(String message) {
            super(message);
        }
    }

    class ReservationFolioIsNotPaidException extends InvalidStateException {
        public ReservationFolioIsNotPaidException() {
            super("reservation folio is not paid");
        }

        public ReservationFolioIsNotPaidException(String message) {
            super(message);
        }
    }
}
