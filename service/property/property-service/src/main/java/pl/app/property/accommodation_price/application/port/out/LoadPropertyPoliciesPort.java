package pl.app.property.accommodation_price.application.port.out;


import pl.app.property.accommodation_price.application.domain.model.PropertyPolicies;

import java.util.UUID;

public interface LoadPropertyPoliciesPort {
    PropertyPolicies loadPropertyPoliciesByPropertyId(UUID propertyId);
}
