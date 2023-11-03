package pl.app.property.reservation.application.port.in;

public interface CancelReservationUseCase {
    void cancelReservation(CancelReservationCommand command);
}
