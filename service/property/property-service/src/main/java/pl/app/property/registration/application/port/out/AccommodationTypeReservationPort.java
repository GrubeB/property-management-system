package pl.app.property.registration.application.port.out;


import pl.app.property.registration.application.domain.model.RegistrationBooking;

import java.util.UUID;

public interface AccommodationTypeReservationPort {
    UUID reserveAccommodationType(RegistrationBooking booking);

    void releaseAccommodationTypeReservation(RegistrationBooking booking);
}
