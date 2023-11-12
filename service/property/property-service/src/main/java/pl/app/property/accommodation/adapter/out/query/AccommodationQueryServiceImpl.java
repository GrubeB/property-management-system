package pl.app.property.accommodation.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.accommodation.adapter.out.persistence.repository.AccommodationRepository;
import pl.app.property.accommodation.adapter.out.query.dto.AccommodationDto;
import pl.app.property.accommodation.adapter.out.query.mapper.AccommodationQueryMapper;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationQueryServiceImpl implements AccommodationQueryService {
    private final AccommodationRepository repository;
    private final AccommodationRepository specificationRepository;
    private final AccommodationQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = Map.of(
            "AccommodationDto", AccommodationDto.class,
            "BaseDto", BaseDto.class
    );
}
