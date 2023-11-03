package pl.app.property.payment.application.domain.exception;


import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface PaymentException {
    class NotFoundPaymentException extends NotFoundException {
        public NotFoundPaymentException() {
            super("not found payment");
        }

        public NotFoundPaymentException(String message) {
            super(message);
        }

        public static NotFoundPaymentException fromId(UUID userId) {
            return new NotFoundPaymentException("not found payment with id: " + userId);
        }

    }
}
