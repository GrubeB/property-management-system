package pl.app.property.reservation_payment_policy.application.port.out;


import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;

import java.util.UUID;

public interface SavePolicyPort {
    UUID savePolicy(ReservationPaymentPolicy policy);
}
