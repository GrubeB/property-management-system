package pl.app.property.accommodation.application.port.out;


import pl.app.property.accommodation.application.domain.model.Accommodation;

import java.util.UUID;

public interface SaveAccommodationPort {
    UUID saveAccommodation(Accommodation accommodation);
}
