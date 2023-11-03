package pl.app.property.user.adapter.out.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.user.adapter.out.persistence.model.UserEntity;
import pl.app.property.user.adapter.out.persistence.repository.UserRepository;
import pl.app.property.user.adapter.out.query.dto.UserDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository repository;
    private final UserRepository specificationRepository;

    private final Map<String, Class<?>> dtoClasses = new HashMap<>() {{
        put("UserDto", UserDto.class);
    }};
    public final Class<UserDto> defaultDtoClass = UserDto.class;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> dtoMappers = new HashMap<>();
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        dtoMappers.put(new AbstractMap.SimpleEntry<>(UserEntity.class, UserDto.class), (UserEntity e) -> modelMapper.map(e, UserDto.class));
    }
}
