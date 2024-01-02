package pl.app.property.accommodation_availability.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.accommodation.adapter.out.query.dto.AccommodationDto;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.adapter.out.persistence.repository.AccommodationTypeAvailabilityEntityRepository;
import pl.app.property.accommodation_availability.adapter.out.query.dto.AccommodationTypeAvailabilityDto;
import pl.app.property.accommodation_availability.adapter.out.query.mapper.AccommodationTypeAvailabilityQueryMapper;
import pl.app.property.accommodation_availability.application.domain.exception.AccommodationAvailabilityException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Getter
class AccommodationTypeAvailabilityQueryServiceImpl implements
        AccommodationTypeAvailabilityQueryService {
    private final AccommodationTypeAvailabilityEntityRepository repository;
    private final AccommodationTypeAvailabilityEntityRepository specificationRepository;
    private final AccommodationTypeAvailabilityQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypeAvailabilityDto", AccommodationTypeAvailabilityDto.class);
        put("BaseDto", BaseDto.class);
    }};

    @Override
    public AccommodationTypeAvailabilityEntity fetchByAccommodationTypeId(UUID accommodationTypeId) {
        return repository.findByAccommodationType_AccommodationTypeId(accommodationTypeId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

}
