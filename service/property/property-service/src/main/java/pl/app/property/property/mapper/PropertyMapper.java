package pl.app.property.property.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.property.dto.PropertyDto;
import pl.app.property.property.model.PropertyEntity;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class PropertyMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(PropertyEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(PropertyEntity.class, PropertyDto.class, e -> modelMapper.map(e, PropertyDto.class));
        addMapper(PropertyDto.class, PropertyEntity.class, e -> modelMapper.map(e, PropertyEntity.class));
    }
}
