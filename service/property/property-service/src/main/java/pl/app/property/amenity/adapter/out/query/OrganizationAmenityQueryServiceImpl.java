package pl.app.property.amenity.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.amenity.adapter.out.persistence.repository.OrganizationAmenityEntityRepository;
import pl.app.property.amenity.adapter.out.query.dto.AmenityDto;
import pl.app.property.amenity.adapter.out.query.dto.OrganizationAmenityDto;
import pl.app.property.amenity.adapter.out.query.mapper.AmenityQueryMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class OrganizationAmenityQueryServiceImpl implements OrganizationAmenityQueryService {
    private final OrganizationAmenityEntityRepository repository;
    private final OrganizationAmenityEntityRepository specificationRepository;
    private final AmenityQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("OrganizationAmenityDto", OrganizationAmenityDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
