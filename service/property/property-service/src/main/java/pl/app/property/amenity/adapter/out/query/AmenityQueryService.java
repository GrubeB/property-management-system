package pl.app.property.amenity.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.amenity.adapter.out.persistence.model.AmenityEntity;

import java.util.UUID;


public interface AmenityQueryService extends
        QueryService.Full<UUID, AmenityEntity> {
}
