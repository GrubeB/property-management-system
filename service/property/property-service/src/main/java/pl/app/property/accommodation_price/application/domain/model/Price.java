package pl.app.property.accommodation_price.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Price {
    private UUID priceId;
    private LocalDate date;
    private BigDecimal price;

    public Price(LocalDate date, BigDecimal price) {
        this.priceId = UUID.randomUUID();
        this.date = date;
        this.price = price;
    }
}
