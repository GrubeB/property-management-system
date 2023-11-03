package pl.app.property.reservation.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.reservation.application.port.out.ReservationPaymentPolicyPort;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;
import pl.app.property.reservation_payment_policy.application.port.in.FetchReservationPaymentPolicyCommand;
import pl.app.property.reservation_payment_policy.application.port.in.FetchReservationPaymentPolicyUseCase;

import java.util.UUID;

@RequiredArgsConstructor
@Component
class ReservationPaymentPolicyAdapter implements
        ReservationPaymentPolicyPort {
    private final FetchReservationPaymentPolicyUseCase fetchReservationPaymentPolicyUseCase;

    @Override
    public ReservationPaymentPolicy fetchReservationPaymentPolicy(UUID propertyId) {
        return fetchReservationPaymentPolicyUseCase.fetch(new FetchReservationPaymentPolicyCommand(propertyId));
    }
}
