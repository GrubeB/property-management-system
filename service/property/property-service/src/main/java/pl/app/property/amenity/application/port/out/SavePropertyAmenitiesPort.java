package pl.app.property.amenity.application.port.out;

import pl.app.property.amenity.application.domain.model.PropertyAmenities;

public interface SavePropertyAmenitiesPort {
    PropertyAmenities save(PropertyAmenities propertyAmenities);
}
