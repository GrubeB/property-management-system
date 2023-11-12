package pl.app.property.amenity.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.amenity.adapter.out.persistence.repository.AmenityEntityRepository;
import pl.app.property.amenity.adapter.out.query.dto.AmenityDto;
import pl.app.property.amenity.adapter.out.query.mapper.AmenityQueryMapper;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AmenityQueryServiceImpl implements AmenityQueryService {
    private final AmenityEntityRepository repository;
    private final AmenityEntityRepository specificationRepository;
    private final AmenityQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = Map.of(
            "AmenityDto", AmenityDto.class,
            "BaseDto", BaseDto.class
    );
}
