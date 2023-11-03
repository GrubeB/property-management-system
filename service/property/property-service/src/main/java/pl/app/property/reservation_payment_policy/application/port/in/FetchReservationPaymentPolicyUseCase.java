package pl.app.property.reservation_payment_policy.application.port.in;

import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;

public interface FetchReservationPaymentPolicyUseCase {
    ReservationPaymentPolicy fetch(FetchReservationPaymentPolicyCommand command);
}
