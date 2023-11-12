package pl.app.property.guest.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.guest.dto.GuestDto;
import pl.app.property.guest.model.GuestEntity;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class GuestMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(GuestEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(GuestEntity.class, GuestDto.class, e -> modelMapper.map(e, GuestDto.class));
        addMapper(GuestDto.class, GuestEntity.class, e -> modelMapper.map(e, GuestEntity.class));
    }
}
