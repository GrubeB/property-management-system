package pl.app.property.accommodation_price_policy.service;

import pl.app.common.core.service.QueryService;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;

import java.util.UUID;

public interface AccommodationTypePriceNumberOfDaysPolicyQueryService extends
        QueryService.Fetchable<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity> {
    AccommodationTypePriceNumberOfDaysPolicyEntity fetchByPropertyIdId(UUID propertyId);
}
