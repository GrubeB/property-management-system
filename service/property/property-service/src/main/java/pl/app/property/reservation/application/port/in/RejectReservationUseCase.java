package pl.app.property.reservation.application.port.in;

public interface RejectReservationUseCase {
    void rejectReservation(RejectReservationCommand command);
}
