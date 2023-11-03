package pl.app.property.accommodation_price.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.property.accommodation_price.application.domain.policy.AccommodationTypePricePolicyType;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Policy {
    private UUID policyId;
    private AccommodationTypePricePolicyType policyType;
    private Boolean isActive;

    public Policy(AccommodationTypePricePolicyType policyType) {
        this.policyId = UUID.randomUUID();
        this.policyType = policyType;
        this.isActive = false;
    }

    public Policy(AccommodationTypePricePolicyType policyType, Boolean isActive) {
        this.policyId = UUID.randomUUID();
        this.policyType = policyType;
        this.isActive = isActive;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
