package pl.app.property.accommodation_price.adapter.out.persistence.mapper;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePriceEntity;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePriceOnSpecificDay;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePricePolicyEntity;
import pl.app.property.accommodation_price.application.domain.model.AccommodationTypePrice;
import pl.app.property.accommodation_price.application.domain.model.Policy;
import pl.app.property.accommodation_price.application.domain.model.Price;
import pl.app.property.accommodation_type.service.AccommodationTypeQueryService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccommodationTypePriceMapper {
    private final AccommodationTypeQueryService accommodationTypeQueryService;
    private final AccommodationTypePricePolicyMapper accommodationTypePricePolicyMapper;

    public AccommodationTypePriceMapper(@Lazy AccommodationTypeQueryService accommodationTypeQueryService, AccommodationTypePricePolicyMapper accommodationTypePricePolicyMapper) {
        this.accommodationTypeQueryService = accommodationTypeQueryService;
        this.accommodationTypePricePolicyMapper = accommodationTypePricePolicyMapper;
    }

    public AccommodationTypePrice mapToAccommodationTypePrice(AccommodationTypePriceEntity entity, List<AccommodationTypePricePolicyEntity> policyEntities) {
        List<Policy> policies = policyEntities.stream().map(accommodationTypePricePolicyMapper::mapToPolicy).collect(Collectors.toList());
        return new AccommodationTypePrice(
                entity.getAccommodationTypePriceId(),
                entity.getAccommodationType().getAccommodationTypeId(),
                entity.getAccommodationType().getProperty().getPropertyId(),
                entity.getDefaultPricePerDay(),
                this.mapToPrices(entity.getAccommodationTypePriceOnSpecificDays()),
                policies
        );
    }

    private Map<LocalDate, Price> mapToPrices(Set<AccommodationTypePriceOnSpecificDay> accommodationTypePriceOnSpecificDays) {
        Map<LocalDate, Price> map = new HashMap<>();
        accommodationTypePriceOnSpecificDays.forEach(price -> map.put(price.getDate(), new Price(price.getPriceId(), price.getDate(), price.getPrice())));
        return map;
    }

    public AccommodationTypePriceEntity mapToAccommodationTypePriceEntity(AccommodationTypePrice domain) {
        AccommodationTypePriceEntity entity = AccommodationTypePriceEntity.builder()
                .accommodationTypePriceId(domain.getAccommodationTypePriceId())
                .accommodationType(accommodationTypeQueryService.fetchById(domain.getAccommodationTypeId()))
                .defaultPricePerDay(domain.getDefaultPricePerDay())
                .accommodationTypePriceOnSpecificDays(domain.getPrices().values().stream().map(this::mapToPriceEntity).collect(Collectors.toSet()))
                .build();
        entity.getAccommodationTypePriceOnSpecificDays().forEach(price -> price.setAccommodationTypePriceEntity(entity));
        return entity;
    }

    private AccommodationTypePriceOnSpecificDay mapToPriceEntity(Price domain) {
        return AccommodationTypePriceOnSpecificDay.builder()
                .priceId(domain.getPriceId())
                .date(domain.getDate())
                .price(domain.getPrice())
                .build();
    }
}
