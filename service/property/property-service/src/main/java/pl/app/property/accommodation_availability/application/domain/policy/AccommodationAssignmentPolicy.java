package pl.app.property.accommodation_availability.application.domain.policy;

import pl.app.property.accommodation_availability.application.domain.model.AccommodationReservation;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;

import java.time.LocalDate;
import java.util.List;

// try to reserve accommodation
// method return 1 or more reservation if reservation succeed otherwise 0
public interface AccommodationAssignmentPolicy {
    List<AccommodationReservation> reserveAccommodation(AccommodationTypeAvailability accommodationTypeAvailability, Integer numberOdAccommodations, LocalDate startDate, LocalDate endDate);
}
