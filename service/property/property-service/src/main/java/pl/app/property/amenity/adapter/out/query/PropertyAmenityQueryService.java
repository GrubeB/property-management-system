package pl.app.property.amenity.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.amenity.adapter.out.persistence.model.PropertyAmenityEntity;

import java.util.UUID;


public interface PropertyAmenityQueryService extends
        QueryService.Fetchable<UUID, PropertyAmenityEntity> {
}
