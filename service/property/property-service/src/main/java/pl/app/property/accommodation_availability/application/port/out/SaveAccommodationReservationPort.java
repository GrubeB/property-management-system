package pl.app.property.accommodation_availability.application.port.out;


import pl.app.property.accommodation_availability.application.domain.model.AccommodationReservation;

import java.util.UUID;

public interface SaveAccommodationReservationPort {
    UUID saveAccommodationReservation(AccommodationReservation accommodationReservation);
}
