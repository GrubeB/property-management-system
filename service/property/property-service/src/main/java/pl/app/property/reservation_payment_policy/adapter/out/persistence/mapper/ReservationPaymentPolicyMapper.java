package pl.app.property.reservation_payment_policy.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.model.ReservationPaymentPolicyEntity;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationPaymentPolicyMapper {
    private final PropertyQueryService propertyQueryService;

    public ReservationPaymentPolicyEntity mapToReservationPaymentPolicyEntity(ReservationPaymentPolicy domain) {
        return ReservationPaymentPolicyEntity.builder()
                .policyId(domain.getPolicyId())
                .type(domain.getType())
                .fixedValue(domain.getFixedValue())
                .numberOfDaysBeforeRegistration(domain.getNumberOfDaysBeforeRegistration())
                .property(this.mapToPropertyEntity(domain.getPropertyId()))
                .build();
    }

    public ReservationPaymentPolicy mapToReservationPaymentPolicy(ReservationPaymentPolicyEntity entity) {
        return new ReservationPaymentPolicy(
                entity.getPolicyId(),
                entity.getProperty().getPropertyId(),
                entity.getType(),
                entity.getFixedValue(),
                entity.getNumberOfDaysBeforeRegistration()
        );
    }

    public PropertyEntity mapToPropertyEntity(UUID propertyId) {
        return propertyQueryService.fetchById(propertyId);
    }
}
