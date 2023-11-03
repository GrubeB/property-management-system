package pl.app.property.accommodation_type.service;


import pl.app.common.core.service.QueryService;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;

import java.util.UUID;

public interface AccommodationTypeQueryService extends
        QueryService.Fetchable<UUID, AccommodationTypeEntity> {
}
