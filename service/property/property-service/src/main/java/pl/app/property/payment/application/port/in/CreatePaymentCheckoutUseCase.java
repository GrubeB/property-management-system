package pl.app.property.payment.application.port.in;

import com.stripe.model.checkout.Session;


public interface CreatePaymentCheckoutUseCase {
    Session createCheckoutSession(CreatePaymentCheckoutCommand command);
}
