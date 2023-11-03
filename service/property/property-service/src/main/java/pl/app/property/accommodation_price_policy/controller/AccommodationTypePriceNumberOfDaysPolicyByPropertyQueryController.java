package pl.app.property.accommodation_price_policy.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;
import pl.app.property.accommodation_price_policy.service.AccommodationTypePriceNumberOfDaysPolicyQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypePriceNumberOfDaysPolicyByPropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypePriceNumberOfDaysPolicyByPropertyQueryController implements
        QueryController.FetchableWithFilter<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity> {
    public static final String resourceName = "number-of-days-policies";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/accommodation-type-price-policies/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "propertyId", "propertyId.property"
    );

    public final AccommodationTypePriceNumberOfDaysPolicyQueryService service;
}
