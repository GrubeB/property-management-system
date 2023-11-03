package pl.app.property.reservation.application.port.in;

public interface ConfirmReservationUseCase {
    void confirmedReservation(ConfirmReservationCommand command);
}
