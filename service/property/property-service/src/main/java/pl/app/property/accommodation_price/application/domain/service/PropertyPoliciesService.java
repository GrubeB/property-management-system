package pl.app.property.accommodation_price.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.accommodation_price.application.domain.model.PropertyPolicies;
import pl.app.property.accommodation_price.application.port.in.ActivateAccommodationTypePricePolicyCommand;
import pl.app.property.accommodation_price.application.port.in.ActivateAccommodationTypePricePolicyUseCase;
import pl.app.property.accommodation_price.application.port.in.DeactivateAccommodationTypePricePolicyCommand;
import pl.app.property.accommodation_price.application.port.in.DeactivateAccommodationTypePricePolicyUseCase;
import pl.app.property.accommodation_price.application.port.out.LoadPropertyPoliciesPort;
import pl.app.property.accommodation_price.application.port.out.SavePropertyPoliciesPort;

@Service
@RequiredArgsConstructor
class PropertyPoliciesService implements
        ActivateAccommodationTypePricePolicyUseCase,
        DeactivateAccommodationTypePricePolicyUseCase {

    private final SavePropertyPoliciesPort savePropertyPoliciesPort;
    private final LoadPropertyPoliciesPort loadPropertyPoliciesPort;

    @Override
    public void activate(ActivateAccommodationTypePricePolicyCommand command) {
        PropertyPolicies propertyPolicies = loadPropertyPoliciesPort.loadPropertyPoliciesByPropertyId(command.getPropertyId());
        propertyPolicies.activatePolicy(command.getPolicyType());
        savePropertyPoliciesPort.savePropertyPoliciesByPropertyId(propertyPolicies);
    }

    @Override
    public void deactivate(DeactivateAccommodationTypePricePolicyCommand command) {
        PropertyPolicies propertyPolicies = loadPropertyPoliciesPort.loadPropertyPoliciesByPropertyId(command.getPropertyId());
        propertyPolicies.deactivatePolicy(command.getPolicyType());
        savePropertyPoliciesPort.savePropertyPoliciesByPropertyId(propertyPolicies);
    }
}
