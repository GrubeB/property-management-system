package pl.app.property.reservation_payment_policy.application.domain.exception;


import pl.app.common.core.service.exception.IllegalArgumentException;
import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface ReservationPaymentPolicyException {
    class NotFoundReservationPaymentPolicyException extends NotFoundException {
        public NotFoundReservationPaymentPolicyException() {
            super("not found reservation payment policy");
        }

        public NotFoundReservationPaymentPolicyException(String message) {
            super(message);
        }

        public static NotFoundReservationPaymentPolicyException fromId(UUID policyId) {
            return new NotFoundReservationPaymentPolicyException("not found reservation payment policy with id: " + policyId);
        }

        public static NotFoundReservationPaymentPolicyException fromPropertyId(UUID propertyId) {
            return new NotFoundReservationPaymentPolicyException("not found reservation payment policy  which is related to party with id: " + propertyId);
        }
    }

    class ReservationPaymentPolicyAlreadyExistsException extends IllegalArgumentException {
        public ReservationPaymentPolicyAlreadyExistsException() {
            super(" ReservationPaymentPolicy already exists for this property");
        }

        public ReservationPaymentPolicyAlreadyExistsException(String message) {
            super(message);
        }
    }
}
