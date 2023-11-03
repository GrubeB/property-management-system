package pl.app.property.accommodation_availability.application.port.in;

public interface IsAccommodationAvailableUseCase {
    Boolean isAccommodationAvailable(IsAccommodationAvailableCommand command);
}
