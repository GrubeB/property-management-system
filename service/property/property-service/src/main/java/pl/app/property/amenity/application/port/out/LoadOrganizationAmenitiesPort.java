package pl.app.property.amenity.application.port.out;

import pl.app.property.amenity.application.domain.model.OrganizationAmenities;

import java.util.UUID;


public interface LoadOrganizationAmenitiesPort {
    OrganizationAmenities loadOrganizationAmenities(UUID organizationId);
}
