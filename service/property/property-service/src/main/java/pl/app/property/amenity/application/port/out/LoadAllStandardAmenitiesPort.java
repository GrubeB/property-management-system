package pl.app.property.amenity.application.port.out;

import pl.app.property.amenity.application.domain.model.Amenity;

import java.util.List;

public interface LoadAllStandardAmenitiesPort {
    List<Amenity> loadAllStandardAmenities();
}
