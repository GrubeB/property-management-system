package pl.app.property.accommodation_price_policy.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.CommandController;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;
import pl.app.property.accommodation_price_policy.service.AccommodationTypePriceNumberOfDaysPolicyService;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypePriceNumberOfDaysPolicyController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypePriceNumberOfDaysPolicyController implements
        CommandController.Creatable.CreatableWithParent<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity, UUID>,
        CommandController.Updatable.UpdatableWithParent<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity, UUID>,
        CommandController.Deletable.SimpleDeletable<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity> {
    public static final String resourceName = "number-of-days-policies";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/accommodation-type-price-policies/" + resourceName;
    private final String parentIdPathVariableName = "propertyId";

    public final AccommodationTypePriceNumberOfDaysPolicyService service;
}
