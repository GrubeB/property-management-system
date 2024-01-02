package pl.app.property.accommodation_type.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.accommodation_type.dto.AccommodationTypeCreateDto;
import pl.app.property.accommodation_type.dto.AccommodationTypeDto;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class AccommodationTypeMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(AccommodationTypeEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationTypeEntity.class, AccommodationTypeDto.class, e -> modelMapper.map(e, AccommodationTypeDto.class));
        addMapper(AccommodationTypeCreateDto.class, AccommodationTypeEntity.class, e -> modelMapper.map(e, AccommodationTypeEntity.class));
    }
}
