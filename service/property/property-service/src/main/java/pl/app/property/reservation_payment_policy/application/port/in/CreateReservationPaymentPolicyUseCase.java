package pl.app.property.reservation_payment_policy.application.port.in;

import java.util.UUID;

public interface CreateReservationPaymentPolicyUseCase {
    UUID createReservationPaymentPolicy(CreateReservationPaymentPolicyCommand command);
}
