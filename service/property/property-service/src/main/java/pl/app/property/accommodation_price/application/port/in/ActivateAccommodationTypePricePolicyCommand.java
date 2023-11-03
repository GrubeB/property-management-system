package pl.app.property.accommodation_price.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.accommodation_price.application.domain.policy.AccommodationTypePricePolicyType;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivateAccommodationTypePricePolicyCommand implements Serializable {
    private UUID propertyId;
    private AccommodationTypePricePolicyType policyType;
}