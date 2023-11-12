package pl.app.property.accommodation_price_policy.service;


import pl.app.common.core.service.CommandService;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;

import java.util.UUID;

public interface AccommodationTypePriceNumberOfDaysPolicyService extends
        CommandService.Creatable.CreatableWithParent<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity, UUID>,
        CommandService.Updatable.UpdatableWithParent<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity, UUID>,
        CommandService.Deletable.SimpleDeletable<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity> {
}
