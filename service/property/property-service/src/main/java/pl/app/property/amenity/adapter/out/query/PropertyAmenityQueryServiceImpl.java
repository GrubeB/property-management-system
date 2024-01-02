package pl.app.property.amenity.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.amenity.adapter.out.persistence.repository.PropertyAmenityEntityRepository;
import pl.app.property.amenity.adapter.out.query.dto.OrganizationAmenityDto;
import pl.app.property.amenity.adapter.out.query.dto.PropertyAmenityDto;
import pl.app.property.amenity.adapter.out.query.mapper.AmenityQueryMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class PropertyAmenityQueryServiceImpl implements PropertyAmenityQueryService {
    private final PropertyAmenityEntityRepository repository;
    private final PropertyAmenityEntityRepository specificationRepository;
    private final AmenityQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("PropertyAmenityDto", PropertyAmenityDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
