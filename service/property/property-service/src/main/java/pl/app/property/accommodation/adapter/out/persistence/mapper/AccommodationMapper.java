package pl.app.property.accommodation.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.accommodation.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation.application.domain.model.Accommodation;
import pl.app.property.accommodation_type.service.AccommodationTypeQueryService;

@Component
@RequiredArgsConstructor
public class AccommodationMapper {
    private final AccommodationTypeQueryService accommodationTypeQueryService;

    public AccommodationEntity mapToAccommodationEntity(Accommodation domain) {
        return AccommodationEntity.builder()
                .accommodationId(domain.getAccommodationId())
                .name(domain.getName())
                .description(domain.getDescription())
                .accommodationType(accommodationTypeQueryService.fetchById(domain.getAccommodationTypeId()))
                .build();
    }

    public Accommodation mapToAccommodation(AccommodationEntity entity) {
        return new Accommodation(
                entity.getAccommodationId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAccommodationType().getAccommodationTypeId()
        );
    }
}
