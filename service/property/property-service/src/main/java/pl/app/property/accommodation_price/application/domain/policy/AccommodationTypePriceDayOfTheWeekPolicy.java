package pl.app.property.accommodation_price.application.domain.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.util.DateUtils;
import pl.app.property.accommodation_price.application.port.out.LoadAccommodationTypePricePolicyPort;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyEntity;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyItemEntity;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.math.RoundingMode.HALF_UP;

@Component
@RequiredArgsConstructor
public class AccommodationTypePriceDayOfTheWeekPolicy implements AccommodationTypePricePolicy {

    private final LoadAccommodationTypePricePolicyPort policyPort;

    @Override
    public BigDecimal calculateDifference(UUID propertyId, UUID accommodationTypeId, BigDecimal defaultPricePerDay, LocalDate start, LocalDate end) {
        AccommodationTypePriceDayOfTheWeekPolicyEntity accommodationTypePriceDayOfTheWeekPolicyEntity = policyPort.loadAccommodationTypePriceDayOfTheWeekPolicyEntityByPropertyId(propertyId);
        Map<DayOfWeek, AccommodationTypePriceDayOfTheWeekPolicyItemEntity> map = accommodationTypePriceDayOfTheWeekPolicyEntity.getItems()
                .stream().collect(Collectors.toMap(AccommodationTypePriceDayOfTheWeekPolicyItemEntity::getDayOfWeek, Function.identity()));
        return DateUtils.getRangeFromDates(start, end).stream()
                .map(DayOfWeek::from)
                .map(map::get)
                .map(dayOfTheWeekEntity -> {
                    BigDecimal constantChange = switch (dayOfTheWeekEntity.getType()) {
                        case NONE -> BigDecimal.ZERO;
                        case INCREASE -> dayOfTheWeekEntity.getConstantValue();
                        case DECREASE -> dayOfTheWeekEntity.getConstantValue().negate();
                    };
                    BigDecimal relativeChange = switch (dayOfTheWeekEntity.getType()) {
                        case NONE -> BigDecimal.ZERO;
                        case INCREASE ->
                                defaultPricePerDay.multiply(dayOfTheWeekEntity.getPercentageValue()).divide(BigDecimal.valueOf(100), 2, HALF_UP);
                        case DECREASE ->
                                defaultPricePerDay.multiply(dayOfTheWeekEntity.getPercentageValue()).divide(BigDecimal.valueOf(100), 2, HALF_UP).negate();
                    };
                    return constantChange.add(relativeChange);
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public AccommodationTypePricePolicyType getType() {
        return AccommodationTypePricePolicyType.DAY_OF_WEEK;
    }
}
