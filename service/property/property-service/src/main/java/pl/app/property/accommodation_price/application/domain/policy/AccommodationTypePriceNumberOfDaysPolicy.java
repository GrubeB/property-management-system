package pl.app.property.accommodation_price.application.domain.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.util.DateUtils;
import pl.app.property.accommodation_price.application.port.out.LoadAccommodationTypePricePolicyPort;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyItemEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

import static java.math.RoundingMode.HALF_UP;

@Component
@RequiredArgsConstructor
public class AccommodationTypePriceNumberOfDaysPolicy implements AccommodationTypePricePolicy {
    private final LoadAccommodationTypePricePolicyPort policyPort;

    @Override
    public BigDecimal calculateDifference(UUID propertyId, UUID accommodationTypeId, BigDecimal defaultPricePerDay, LocalDate start, LocalDate end) {
        AccommodationTypePriceNumberOfDaysPolicyEntity policyEntity = policyPort.loadAccommodationTypePriceNumberOfDaysPolicyEntityByPropertyId(propertyId);

        int numberOfDays = DateUtils.getRangeFromDates(start, end).size();
        Optional<AccommodationTypePriceNumberOfDaysPolicyItemEntity> max = policyEntity.getItems().stream()
                .filter(numberOfDaysEntity -> numberOfDaysEntity.getNumberOfDays() <= numberOfDays)
                .max(Comparator.comparing(AccommodationTypePriceNumberOfDaysPolicyItemEntity::getNumberOfDays));

        return max.stream().map(numberOfDaysEntity -> {
            BigDecimal constantChange = switch (numberOfDaysEntity.getType()) {
                case NONE -> BigDecimal.ZERO;
                case INCREASE -> numberOfDaysEntity.getConstantValue();
                case DECREASE -> numberOfDaysEntity.getConstantValue().negate();
            };
            BigDecimal relativeChange = switch (numberOfDaysEntity.getType()) {
                case NONE -> BigDecimal.ZERO;
                case INCREASE ->
                        defaultPricePerDay.multiply(numberOfDaysEntity.getPercentageValue()).divide(BigDecimal.valueOf(100), 2, HALF_UP);
                case DECREASE ->
                        defaultPricePerDay.multiply(numberOfDaysEntity.getPercentageValue()).divide(BigDecimal.valueOf(100), 2, HALF_UP).negate();
            };
            return constantChange.add(relativeChange);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public AccommodationTypePricePolicyType getType() {
        return AccommodationTypePricePolicyType.NUMBER_OF_DAYS;
    }
}
