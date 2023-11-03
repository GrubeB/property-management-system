package pl.app.property.payment.application.port.in;

import java.util.UUID;


public interface PaymentCheckoutFailedUseCase {
    void checkoutSessionFailed(UUID paymentId);
}
