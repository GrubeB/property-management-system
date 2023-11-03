package pl.app.property.reservation.application.port.in;

public interface RejectNoConfirmedReservationsUseCase {
    void rejectNoConfirmedReservations(RejectNoConfirmedReservationsCommand command);
}
