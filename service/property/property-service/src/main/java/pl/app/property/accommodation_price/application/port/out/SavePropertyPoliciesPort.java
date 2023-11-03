package pl.app.property.accommodation_price.application.port.out;


import pl.app.property.accommodation_price.application.domain.model.PropertyPolicies;

public interface SavePropertyPoliciesPort {
    void savePropertyPoliciesByPropertyId(PropertyPolicies propertyPolicies);
}
