package pl.app.property.registration_folio.application.domain.exception;


import pl.app.common.core.service.exception.InvalidStateException;
import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface RegistrationFolioException {
    class NotFoundRegistrationFolioException extends NotFoundException {
        public NotFoundRegistrationFolioException() {
            super("not found reservation folio");
        }

        public NotFoundRegistrationFolioException(String message) {
            super(message);
        }

        public static NotFoundRegistrationFolioException fromId(UUID registrationFolioId) {
            return new NotFoundRegistrationFolioException("not found registration folio with id: " + registrationFolioId);
        }
    }

    class NotFoundPartyFolioException extends NotFoundException {
        public NotFoundPartyFolioException() {
            super("not found party folio");
        }

        public NotFoundPartyFolioException(String message) {
            super(message);
        }

        public static NotFoundPartyFolioException fromId(UUID partyFolioId) {
            return new NotFoundPartyFolioException("not found party folio with id: " + partyFolioId);
        }

        public static NotFoundPartyFolioException fromPartyId(UUID partyId) {
            return new NotFoundPartyFolioException("not found party folio which is related to party with id: " + partyId);
        }
    }

    class RegistrationFolioWrongStatedException extends InvalidStateException {
        public RegistrationFolioWrongStatedException() {
            super("registration has wrong state");
        }

        public RegistrationFolioWrongStatedException(String message) {
            super(message);
        }
    }

    class NotFoundPartyFolioChargeException extends NotFoundException {
        public NotFoundPartyFolioChargeException() {
            super("not found party folio charge");
        }

        public NotFoundPartyFolioChargeException(String message) {
            super(message);
        }

        public static NotFoundPartyFolioChargeException fromId(UUID chargeId) {
            return new NotFoundPartyFolioChargeException("not found party folio charge with id: " + chargeId);
        }
    }
}
