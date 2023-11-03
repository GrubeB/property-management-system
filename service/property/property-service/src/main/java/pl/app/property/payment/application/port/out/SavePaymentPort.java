package pl.app.property.payment.application.port.out;


import pl.app.property.payment.application.domain.model.Payment;

import java.util.UUID;

public interface SavePaymentPort {
    UUID savePayment(Payment payment);
}
