package pl.app.property.accommodation_price.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.property.accommodation_price.application.domain.policy.AccommodationTypePricePolicyType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PropertyPolicies {
    private UUID propertyId;
    private List<Policy> policies;

    public void activatePolicy(AccommodationTypePricePolicyType policyType) {
        Policy policy = this.getPolicyByType(policyType).orElse(new Policy(policyType));
        policy.activate();
        policies.add(policy);
    }

    public void deactivatePolicy(AccommodationTypePricePolicyType policyType) {
        Policy policy = this.getPolicyByType(policyType).orElse(new Policy(policyType));
        policy.deactivate();
        policies.add(policy);
    }

    public Optional<Policy> getPolicyByType(AccommodationTypePricePolicyType type) {
        return policies.stream().filter(policy -> type.equals(policy.getPolicyType())).findAny();
    }
}

