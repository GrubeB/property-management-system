package pl.app.property.reservation_payment_policy.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.mapper.ReservationPaymentPolicyMapper;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.model.ReservationPaymentPolicyEntity;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.repositroy.ReservationPaymentPolicyRepository;
import pl.app.property.reservation_payment_policy.application.domain.exception.ReservationPaymentPolicyException;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;
import pl.app.property.reservation_payment_policy.application.port.out.LoadPolicyPort;
import pl.app.property.reservation_payment_policy.application.port.out.SavePolicyPort;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class ReservationPaymentPolicyPersistenceAdapter implements
        LoadPolicyPort,
        SavePolicyPort {
    private final ReservationPaymentPolicyRepository reservationPaymentPolicyRepository;
    private final ReservationPaymentPolicyMapper reservationPaymentPolicyMapper;

    @Override
    public ReservationPaymentPolicy loadPolicy(UUID policyId) {
        ReservationPaymentPolicyEntity entity = reservationPaymentPolicyRepository.findById(policyId)
                .orElseThrow(() -> ReservationPaymentPolicyException.NotFoundReservationPaymentPolicyException.fromId(policyId));
        return reservationPaymentPolicyMapper.mapToReservationPaymentPolicy(entity);
    }

    @Override
    public ReservationPaymentPolicy loadPolicyByPropertyId(UUID propertyId) {
        ReservationPaymentPolicyEntity entity = reservationPaymentPolicyRepository.findByProperty_PropertyId(propertyId)
                .orElseThrow(() -> ReservationPaymentPolicyException.NotFoundReservationPaymentPolicyException.fromPropertyId(propertyId));
        return reservationPaymentPolicyMapper.mapToReservationPaymentPolicy(entity);
    }

    @Override
    public UUID savePolicy(ReservationPaymentPolicy policy) {
        ReservationPaymentPolicyEntity entity = reservationPaymentPolicyMapper.mapToReservationPaymentPolicyEntity(policy);
        reservationPaymentPolicyRepository.save(entity);
        return entity.getPolicyId();
    }
}
