package pl.app.property.accommodation_price_policy.service;


import pl.app.common.core.service.CommandService;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyEntity;

import java.util.UUID;

public interface AccommodationTypePriceDayOfTheWeekPolicyService extends
        CommandService.Creatable.CreatableWithParent<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity, UUID>,
        CommandService.Updatable.UpdatableWithParent<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity, UUID>,
        CommandService.Deletable.SimpleDeletable<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity> {
}
