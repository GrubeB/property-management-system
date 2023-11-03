package pl.app.property.accommodation_price_policy.service;


import pl.app.common.core.service.SimpleCommandService;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyEntity;

import java.util.UUID;

public interface AccommodationTypePriceDayOfTheWeekPolicyService extends
        SimpleCommandService.CreatableWithParent<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity>,
        SimpleCommandService.Updatable<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity>,
        SimpleCommandService.DeletableById<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity> {
}
