package pl.app.property.amenity.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.amenity.adapter.out.persistence.model.OrganizationAmenityEntity;

import java.util.UUID;


public interface OrganizationAmenityQueryService extends
        QueryService.Fetchable<UUID, OrganizationAmenityEntity> {
}
