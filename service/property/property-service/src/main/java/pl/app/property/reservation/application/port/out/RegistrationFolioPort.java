package pl.app.property.reservation.application.port.out;

import pl.app.property.reservation.application.domain.model.Reservation;

import java.util.UUID;

public interface RegistrationFolioPort {
    UUID createRegistration(Reservation reservation);
}
