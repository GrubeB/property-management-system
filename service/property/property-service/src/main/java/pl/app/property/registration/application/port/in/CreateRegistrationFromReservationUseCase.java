package pl.app.property.registration.application.port.in;

import java.util.UUID;

public interface CreateRegistrationFromReservationUseCase {
    UUID createRegistrationFromReservation(CreateRegistrationFromReservationCommand command);
}
