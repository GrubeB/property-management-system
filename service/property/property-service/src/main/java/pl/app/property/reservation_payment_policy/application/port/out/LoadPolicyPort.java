package pl.app.property.reservation_payment_policy.application.port.out;


import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;

import java.util.UUID;

public interface LoadPolicyPort {
    ReservationPaymentPolicy loadPolicy(UUID policyId);

    ReservationPaymentPolicy loadPolicyByPropertyId(UUID propertyId);
}
