package pl.app.property.accommodation.application.port.in;

import java.util.UUID;

public interface AddAccommodationUseCase {
    UUID addAccommodation(AddAccommodationCommand command);
}
