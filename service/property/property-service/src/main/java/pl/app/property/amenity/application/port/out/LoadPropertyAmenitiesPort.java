package pl.app.property.amenity.application.port.out;

import pl.app.property.amenity.application.domain.model.PropertyAmenities;

import java.util.UUID;

public interface LoadPropertyAmenitiesPort {
    PropertyAmenities loadPropertyAmenities(UUID propertyId);
}
