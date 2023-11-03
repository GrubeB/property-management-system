package pl.app.property.accommodation_availability.application.port.out;


import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;

import java.util.UUID;

public interface SaveAccommodationAvailabilityPort {
    UUID saveAccommodationAvailability(AccommodationTypeAvailability accommodationTypeAvailability);
}
