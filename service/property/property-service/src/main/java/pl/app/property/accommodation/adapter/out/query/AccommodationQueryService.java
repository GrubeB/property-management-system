package pl.app.property.accommodation.adapter.out.query;


import pl.app.common.core.service.QueryService;
import pl.app.property.accommodation.adapter.out.persistence.model.AccommodationEntity;

import java.util.UUID;

public interface AccommodationQueryService extends
        QueryService.Full<UUID, AccommodationEntity> {
}
