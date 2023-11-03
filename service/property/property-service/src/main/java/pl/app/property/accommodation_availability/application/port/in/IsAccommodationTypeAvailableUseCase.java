package pl.app.property.accommodation_availability.application.port.in;

public interface IsAccommodationTypeAvailableUseCase {
    Boolean isAccommodationTypeAvailable(IsAccommodationTypeAvailableCommand command);
}
