package pl.app.property.accommodation_price.application.domain.policy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

// return the difference in the price of accommodation in a given range of days
public interface AccommodationTypePricePolicy {
    BigDecimal calculateDifference(UUID propertyId, UUID accommodationTypeId, BigDecimal defaultPricePerDay, LocalDate start, LocalDate end);

    AccommodationTypePricePolicyType getType();
}
