package pl.app.property.accommodation_availability.application.port.in;

public interface ChangeAccommodationReservationStatusUseCase {
    void changeAccommodationReservationStatus(ChangeAccommodationReservationStatusCommand command);
}
