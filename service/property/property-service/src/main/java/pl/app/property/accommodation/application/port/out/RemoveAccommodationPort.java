package pl.app.property.accommodation.application.port.out;

import java.util.UUID;

public interface RemoveAccommodationPort {
    void removeAccommodation(UUID accommodationId);
}
