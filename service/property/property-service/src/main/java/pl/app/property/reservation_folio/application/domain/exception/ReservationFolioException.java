package pl.app.property.reservation_folio.application.domain.exception;


import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface ReservationFolioException {
    class NotFoundReservationFolioException extends NotFoundException {
        public NotFoundReservationFolioException() {
            super("not found reservation folio");
        }

        public NotFoundReservationFolioException(String message) {
            super(message);
        }

        public static NotFoundReservationFolioException fromId(UUID registrationFolioId) {
            return new NotFoundReservationFolioException("not found reservation folio with id: " + registrationFolioId);
        }
    }

    class NotFoundReservationFolioChargeException extends NotFoundException {
        public NotFoundReservationFolioChargeException() {
            super("not found reservation folio charge");
        }

        public NotFoundReservationFolioChargeException(String message) {
            super(message);
        }

        public static NotFoundReservationFolioChargeException fromId(UUID registrationFolioChargeId) {
            return new NotFoundReservationFolioChargeException("not found reservation folio charge with id: " + registrationFolioChargeId);
        }
    }
}
