package pl.app.property.reservation.application.port.out;

import pl.app.property.reservation.application.domain.model.ReservationBooking;

import java.util.UUID;

public interface AccommodationTypeReservationPort {
    UUID reserveAccommodationType(ReservationBooking booking);

    void releaseAccommodationTypeReservation(ReservationBooking booking);
}
