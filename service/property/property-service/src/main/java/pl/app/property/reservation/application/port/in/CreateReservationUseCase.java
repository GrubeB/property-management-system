package pl.app.property.reservation.application.port.in;

import java.util.UUID;

public interface CreateReservationUseCase {
    UUID createReservation(CreateReservationCommand command);
}
