package pl.app.property.accommodation_price.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePriceEntity;

import java.util.UUID;

public interface AccommodationTypePriceQueryService extends
        QueryService.Full<UUID, AccommodationTypePriceEntity> {
    AccommodationTypePriceEntity fetchByAccommodationTypeId(UUID accommodationTypeId);
}
