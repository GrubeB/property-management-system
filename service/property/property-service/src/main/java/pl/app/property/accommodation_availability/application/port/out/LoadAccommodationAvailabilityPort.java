package pl.app.property.accommodation_availability.application.port.out;


import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;

import java.time.LocalDate;
import java.util.UUID;

public interface LoadAccommodationAvailabilityPort {
    AccommodationTypeAvailability loadAccommodationAvailability(UUID accommodationTypeAvailabilityId);

    AccommodationTypeAvailability loadAccommodationAvailability(UUID accommodationTypeAvailabilityId, LocalDate startDate, LocalDate endDate);

    AccommodationTypeAvailability loadAccommodationAvailabilityByAccommodationTypeId(UUID accommodationTypeId, LocalDate startDate, LocalDate endDate);

    AccommodationTypeAvailability loadAccommodationAvailabilityByAccommodationId(UUID accommodationId, LocalDate startDate, LocalDate endDate);

    AccommodationTypeAvailability loadAccommodationAvailabilityByAccommodationTypeReservationId(UUID accommodationTypeReservationId);

    AccommodationTypeAvailability loadAccommodationAvailabilityByAccommodationReservationId(UUID accommodationReservationId);
}
