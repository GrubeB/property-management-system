package pl.app.property.accommodation_type.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.accommodation.adapter.out.query.dto.AccommodationDto;
import pl.app.property.accommodation_type.dto.AccommodationTypeDto;
import pl.app.property.accommodation_type.mapper.AccommodationTypeMapper;
import pl.app.property.accommodation_type.persistence.AccommodationTypeRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationTypeQueryServiceImpl implements
        AccommodationTypeQueryService {
    private final AccommodationTypeRepository repository;
    private final AccommodationTypeRepository specificationRepository;
    private final AccommodationTypeMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("AccommodationTypeDto", AccommodationTypeDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
