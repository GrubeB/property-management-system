package pl.app.property.accommodation_price_policy.service;


import pl.app.common.core.service.QueryService;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyEntity;

import java.util.UUID;

public interface AccommodationTypePriceDayOfTheWeekPolicyQueryService extends
        QueryService.Fetchable<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity> {
    AccommodationTypePriceDayOfTheWeekPolicyEntity fetchByPropertyId(UUID propertyId);
}
