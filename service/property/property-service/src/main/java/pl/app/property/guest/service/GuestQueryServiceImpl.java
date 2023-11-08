package pl.app.property.guest.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.guest.dto.GuestDto;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.guest.persistence.GuestRepository;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class GuestQueryServiceImpl implements GuestQueryService {
    private final GuestRepository repository;
    private final GuestRepository specificationRepository;

    private final Map<String, Class<?>> dtoClasses = Map.of(
            "BaseDto", BaseDto.class,
            "GuestDto", GuestDto.class
    );
    public final Class<BaseDto> defaultDtoClass = BaseDto.class;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> dtoMappers = new HashMap<>();
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        dtoMappers.put(new AbstractMap.SimpleEntry<>(GuestEntity.class, BaseDto.class),
                (GuestEntity e) -> modelMapper.map(e, BaseDto.class));
        dtoMappers.put(new AbstractMap.SimpleEntry<>(GuestEntity.class, GuestDto.class),
                (GuestEntity e) -> modelMapper.map(e, GuestDto.class));
    }
}
