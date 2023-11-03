package pl.app.property.accommodation_price.application.port.in;

import java.math.BigDecimal;

public interface CalculateAccommodationTypePriceUseCase {
    BigDecimal calculateAccommodationTypePrice(CalculateAccommodationTypePriceCommand command);
}
