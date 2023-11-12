package pl.app.property.amenity.adapter.out.query.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.amenity.adapter.out.persistence.model.AmenityEntity;
import pl.app.property.amenity.adapter.out.persistence.model.OrganizationAmenityEntity;
import pl.app.property.amenity.adapter.out.persistence.model.PropertyAmenityEntity;
import pl.app.property.amenity.adapter.out.query.dto.AmenityDto;
import pl.app.property.amenity.adapter.out.query.dto.OrganizationAmenityDto;
import pl.app.property.amenity.adapter.out.query.dto.PropertyAmenityDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class AmenityQueryMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(AmenityEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AmenityEntity.class, AmenityDto.class, e -> modelMapper.map(e, AmenityDto.class));

        addMapper(OrganizationAmenityEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(OrganizationAmenityEntity.class, OrganizationAmenityDto.class, e -> modelMapper.map(e, OrganizationAmenityDto.class));

        addMapper(PropertyAmenityEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(PropertyAmenityEntity.class, PropertyAmenityDto.class, e -> modelMapper.map(e, PropertyAmenityDto.class));
    }
}
