package pl.app.property.payment.application.port.out;


import pl.app.property.payment.application.domain.model.StripePayment;

import java.util.UUID;

public interface LoadStripePaymentPort {
    StripePayment loadStripePayment(UUID paymentId);
}
