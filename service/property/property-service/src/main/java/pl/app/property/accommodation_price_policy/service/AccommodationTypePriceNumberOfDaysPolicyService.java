package pl.app.property.accommodation_price_policy.service;


import pl.app.common.core.service.SimpleCommandService;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;

import java.util.UUID;

public interface AccommodationTypePriceNumberOfDaysPolicyService extends
        SimpleCommandService.CreatableWithParent<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity>,
        SimpleCommandService.Updatable<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity>,
        SimpleCommandService.DeletableById<UUID, AccommodationTypePriceNumberOfDaysPolicyEntity> {
}
