package pl.app.property.accommodation_price.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.common.util.DateUtils;
import pl.app.property.accommodation_price.application.domain.policy.AccommodationTypePricePolicy;
import pl.app.property.accommodation_price.application.domain.policy.AccommodationTypePricePolicyType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Getter
@AllArgsConstructor
public class AccommodationTypePrice {
    private UUID accommodationTypePriceId;
    private UUID accommodationTypeId;
    private UUID propertyId;
    private BigDecimal defaultPricePerDay;
    private Map<LocalDate, Price> prices;
    private List<Policy> policies;

    public AccommodationTypePrice(UUID accommodationTypeId, BigDecimal defaultPricePerDay) {
        this.accommodationTypePriceId = UUID.randomUUID();
        this.accommodationTypeId = accommodationTypeId;
        this.defaultPricePerDay = defaultPricePerDay;
        this.prices = new HashMap<>();
        this.policies = new ArrayList<>();
    }

    public BigDecimal getDefaultPriceForDateRange(LocalDate start, LocalDate end) {
        int numberOfDays = DateUtils.getRangeFromDates(start, end).size();
        return defaultPricePerDay.multiply(BigDecimal.valueOf(numberOfDays));
    }

    public BigDecimal gerPriceDifferenceForDateRange(LocalDate start, LocalDate end, Map<AccommodationTypePricePolicyType, AccommodationTypePricePolicy> policies) {
        return getActivePolicyTypes().stream()
                .map(policies::get)
                .map(policy -> policy.calculateDifference(propertyId, accommodationTypeId, defaultPricePerDay, start, end))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal gerPriceDifferenceForDate(LocalDate date, Map<AccommodationTypePricePolicyType, AccommodationTypePricePolicy> policies) {
        return getActivePolicyTypes().stream()
                .map(policies::get)
                .map(policy -> policy.calculateDifference(propertyId, accommodationTypeId, defaultPricePerDay, date, date))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal gerPriceForDateRange(LocalDate start, LocalDate end, Map<AccommodationTypePricePolicyType, AccommodationTypePricePolicy> policies) {
        return getDefaultPriceForDateRange(start, end)
                .add(gerPriceDifferenceForDateRange(start, end, policies));
    }

    public void generatePricesFromRange(LocalDate fromDate, LocalDate toDate, Map<AccommodationTypePricePolicyType, AccommodationTypePricePolicy> policies) {
        DateUtils.getRangeFromDates(fromDate, toDate)
                .forEach(date -> {
                    BigDecimal priceDifference = gerPriceDifferenceForDate(date, policies);
                    this.prices.put(date, new Price(date, this.defaultPricePerDay.add(priceDifference)));
                });
    }

    public List<AccommodationTypePricePolicyType> getActivePolicyTypes() {
        return this.policies.stream()
                .filter(Policy::getIsActive)
                .map(Policy::getPolicyType)
                .toList();
    }
}

