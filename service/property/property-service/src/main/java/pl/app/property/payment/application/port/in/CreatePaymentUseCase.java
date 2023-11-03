package pl.app.property.payment.application.port.in;

import java.util.UUID;

public interface CreatePaymentUseCase {
    UUID createPayment(CreatePaymentCommand command);
}
