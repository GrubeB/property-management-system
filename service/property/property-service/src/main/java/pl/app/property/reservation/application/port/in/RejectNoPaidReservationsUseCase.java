package pl.app.property.reservation.application.port.in;

public interface RejectNoPaidReservationsUseCase {
    void rejectNoPaidReservations(RejectNoPaidReservationsCommand command);
}
