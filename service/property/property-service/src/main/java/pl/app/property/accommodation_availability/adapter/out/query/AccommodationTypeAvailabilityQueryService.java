package pl.app.property.accommodation_availability.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeAvailabilityEntity;

import java.util.UUID;


public interface AccommodationTypeAvailabilityQueryService extends
        QueryService.Full<UUID, AccommodationTypeAvailabilityEntity> {
    AccommodationTypeAvailabilityEntity fetchByAccommodationTypeId(UUID accommodationTypeId);
}
