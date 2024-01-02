package pl.app.property.accommodation_price.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.accommodation_availability.adapter.out.query.dto.AccommodationTypeAvailabilityDto;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePriceEntity;
import pl.app.property.accommodation_price.adapter.out.persistence.repository.AccommodationTypePriceEntityRepository;
import pl.app.property.accommodation_price.adapter.out.query.dto.AccommodationTypePriceDto;
import pl.app.property.accommodation_price.adapter.out.query.mapper.AccommodationTypePriceQueryMapper;
import pl.app.property.accommodation_price.application.domain.exception.AccommodationTypePriceException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationTypePriceQueryServiceImpl implements AccommodationTypePriceQueryService {

    private final AccommodationTypePriceEntityRepository repository;
    private final AccommodationTypePriceEntityRepository specificationRepository;
    private final AccommodationTypePriceQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypePriceDto", AccommodationTypePriceDto.class);
        put("BaseDto", BaseDto.class);
    }};

    @Override
    public AccommodationTypePriceEntity fetchByAccommodationTypeId(UUID accommodationTypeId) {
        return repository.findByAccommodationType_AccommodationTypeId(accommodationTypeId)
                .orElseThrow(AccommodationTypePriceException.NotFoundAccommodationTypePriceException::new);
    }

}
