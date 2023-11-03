package pl.app.property.accommodation_availability.application.port.in;

import java.util.UUID;

public interface CreateAccommodationTypeReservationUseCase {
    UUID createAccommodationTypeReservation(CreateAccommodationTypeReservationCommand command);
}
