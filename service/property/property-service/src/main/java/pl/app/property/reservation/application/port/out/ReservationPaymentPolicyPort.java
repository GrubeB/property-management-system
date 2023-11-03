package pl.app.property.reservation.application.port.out;

import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;

import java.util.UUID;

public interface ReservationPaymentPolicyPort {
    ReservationPaymentPolicy fetchReservationPaymentPolicy(UUID propertyId);
}
