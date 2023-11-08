package pl.app.property.property.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.property.dto.PropertyDto;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.persistence.PropertyRepository;

import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class PropertyQueryServiceImpl implements PropertyQueryService {
    private final PropertyRepository repository;
    private final PropertyRepository specificationRepository;

    private final Map<String, Class<?>> dtoClasses = Map.of(
            "BaseDto", BaseDto.class,
            "PropertyDto", PropertyDto.class
    );
    public final Class<BaseDto> defaultDtoClass = BaseDto.class;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> dtoMappers = new HashMap<>();
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        dtoMappers.put(new AbstractMap.SimpleEntry<>(PropertyEntity.class, BaseDto.class),
                (PropertyEntity e) -> modelMapper.map(e, PropertyDto.class));
        dtoMappers.put(new AbstractMap.SimpleEntry<>(PropertyEntity.class, PropertyDto.class),
                (PropertyEntity e) -> modelMapper.map(e, PropertyDto.class));
    }

    @Override
    public List<UUID> fetchIdAll() {
        return repository.findIdAll();
    }
}
