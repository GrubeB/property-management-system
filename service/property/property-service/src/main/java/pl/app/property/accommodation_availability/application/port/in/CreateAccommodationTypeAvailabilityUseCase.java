package pl.app.property.accommodation_availability.application.port.in;

import java.util.UUID;

public interface CreateAccommodationTypeAvailabilityUseCase {
    UUID create(CreateAccommodationTypeAvailabilityCommand command);
}
