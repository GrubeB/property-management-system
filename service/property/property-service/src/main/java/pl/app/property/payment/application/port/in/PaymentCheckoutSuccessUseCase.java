package pl.app.property.payment.application.port.in;

import java.util.UUID;


public interface PaymentCheckoutSuccessUseCase {
    void checkoutSessionSuccess(UUID paymentId);
}
