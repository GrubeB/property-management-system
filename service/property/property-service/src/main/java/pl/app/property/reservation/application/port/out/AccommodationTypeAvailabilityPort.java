package pl.app.property.reservation.application.port.out;

import pl.app.property.reservation.application.domain.model.Reservation;

public interface AccommodationTypeAvailabilityPort {
    boolean isAccommodationTypeAvailable(Reservation reservation);
}
