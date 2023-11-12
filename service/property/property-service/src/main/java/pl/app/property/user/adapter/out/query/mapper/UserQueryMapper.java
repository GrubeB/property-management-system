package pl.app.property.user.adapter.out.query.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.user.adapter.out.persistence.model.UserEntity;
import pl.app.property.user.adapter.out.query.dto.UserDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class UserQueryMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(UserEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(UserEntity.class, UserDto.class, e -> modelMapper.map(e, UserDto.class));
    }
}
