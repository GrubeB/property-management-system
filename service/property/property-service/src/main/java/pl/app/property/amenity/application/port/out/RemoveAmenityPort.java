package pl.app.property.amenity.application.port.out;

import java.util.UUID;

public interface RemoveAmenityPort {
    void delete(UUID amenityId);
}
