package pl.app.property.reservation.application.port.in;

import java.util.UUID;

public interface FinishReservationUseCase {
    UUID finishReservation(FinishReservationCommand command);
}
