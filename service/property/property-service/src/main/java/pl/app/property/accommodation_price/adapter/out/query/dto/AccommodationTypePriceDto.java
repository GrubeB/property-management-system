package pl.app.property.accommodation_price.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePriceOnSpecificDay;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationTypePriceDto implements Serializable {
    private UUID accommodationTypePriceId;
    private BaseDto accommodationType;
    private BigDecimal defaultPricePerDay;
    private Set<AccommodationTypePriceOnSpecificDay> accommodationTypePriceOnSpecificDays;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationTypePriceOnSpecificDayDto implements Serializable {
        private UUID priceId;
        private LocalDate date;
        private BigDecimal price;
    }
}
