package pl.app.property.accommodation_availability.adapter.out.query.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.adapter.out.query.dto.AccommodationTypeAvailabilityDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class AccommodationTypeAvailabilityQueryMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(AccommodationTypeAvailabilityEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(AccommodationTypeAvailabilityEntity.class, AccommodationTypeAvailabilityDto.class, e -> modelMapper.map(e, AccommodationTypeAvailabilityDto.class));
    }
}
