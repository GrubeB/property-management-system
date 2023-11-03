package pl.app.property.amenity.application.port.out;

import pl.app.property.amenity.application.domain.model.Amenity;

import java.util.UUID;

public interface LoadAmenityPort {
    Amenity loadAmenity(UUID amenityId);
}
