package pl.app.property.accommodation_price.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccommodationTypePriceCommand implements Serializable {
    private UUID accommodationType;
    private BigDecimal defaultPricePerDay;
}