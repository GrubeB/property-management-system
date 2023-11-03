package pl.app.property.amenity.application.port.out;

import pl.app.property.amenity.application.domain.model.OrganizationAmenities;

public interface SaveOrganizationAmenitiesPort {
    OrganizationAmenities save(OrganizationAmenities organizationAmenities);
}
