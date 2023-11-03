package pl.app.property.accommodation_price.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.property.accommodation_price.application.domain.policy.AccommodationTypePricePolicyType;
import pl.app.property.accommodation_price.application.port.in.ActivateAccommodationTypePricePolicyCommand;
import pl.app.property.accommodation_price.application.port.in.ActivateAccommodationTypePricePolicyUseCase;
import pl.app.property.accommodation_price.application.port.in.DeactivateAccommodationTypePricePolicyCommand;
import pl.app.property.accommodation_price.application.port.in.DeactivateAccommodationTypePricePolicyUseCase;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypePricePolicyController.resourcePath)
@RequiredArgsConstructor
public class AccommodationTypePricePolicyController {

    public static final String resourceName = "accommodation-type-price-policies";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    public static final String activatePath = "/{policyType}/activate";
    public static final String deactivatePath = "/{policyType}/deactivate";

    private final ActivateAccommodationTypePricePolicyUseCase activateAccommodationTypePricePolicyUseCase;
    private final DeactivateAccommodationTypePricePolicyUseCase deactivateAccommodationTypePricePolicyUseCase;


    @PostMapping(path = activatePath)
    public ResponseEntity<Void> activate(@PathVariable UUID propertyId, @PathVariable AccommodationTypePricePolicyType policyType) {
        activateAccommodationTypePricePolicyUseCase.activate(new ActivateAccommodationTypePricePolicyCommand(propertyId, policyType));
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = deactivatePath)
    public ResponseEntity<Void> deactivate(@PathVariable UUID propertyId, @PathVariable AccommodationTypePricePolicyType policyType) {
        deactivateAccommodationTypePricePolicyUseCase.deactivate(new DeactivateAccommodationTypePricePolicyCommand(propertyId, policyType));
        return ResponseEntity
                .noContent()
                .build();
    }
}
