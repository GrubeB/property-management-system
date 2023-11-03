package pl.app.property.accommodation_availability.application.domain.policy;

import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;

import java.time.LocalDate;

// checks if there will be accommodation available for a given period of time
public interface AccommodationTypeAvailabilityPolicy {
    boolean isAccommodationTypeAvailable(AccommodationTypeAvailability accommodationTypeAvailability, Integer numberOdAccommodations, LocalDate startDate, LocalDate endDate);
}