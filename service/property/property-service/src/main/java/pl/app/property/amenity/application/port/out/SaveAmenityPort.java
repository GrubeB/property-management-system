package pl.app.property.amenity.application.port.out;

import pl.app.property.amenity.application.domain.model.Amenity;

public interface SaveAmenityPort {
    Amenity save(Amenity amenity);
}
