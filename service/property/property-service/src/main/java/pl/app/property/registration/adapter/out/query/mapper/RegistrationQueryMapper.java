package pl.app.property.registration.adapter.out.query.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationEntity;
import pl.app.property.registration.adapter.out.query.dto.RegistrationDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class RegistrationQueryMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(RegistrationEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(RegistrationEntity.class, RegistrationDto.class, e -> modelMapper.map(e, RegistrationDto.class));
    }
}
