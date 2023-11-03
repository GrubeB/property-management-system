package pl.app.property.accommodation_price.application.port.out;


import pl.app.property.accommodation_price.application.domain.model.AccommodationTypePrice;

import java.util.UUID;

public interface SaveAccommodationTypePricePort {
    UUID saveAccommodationTypePrice(AccommodationTypePrice domain);
}
