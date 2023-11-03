package pl.app.property.accommodation_price.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePricePolicyEntity;
import pl.app.property.accommodation_price.application.domain.model.Policy;

@Component
public class AccommodationTypePricePolicyMapper {

    public Policy mapToPolicy(AccommodationTypePricePolicyEntity entity) {
        return new Policy(
                entity.getAccommodationTypePricePolicyId(),
                entity.getPolicyType(),
                entity.getIsActive());
    }

    public AccommodationTypePricePolicyEntity mapToAccommodationTypePricePolicyEntity(Policy policy) {
        return AccommodationTypePricePolicyEntity.builder()
                .accommodationTypePricePolicyId(policy.getPolicyId())
                .policyType(policy.getPolicyType())
                .isActive(policy.getIsActive())
                .build();
    }
}
