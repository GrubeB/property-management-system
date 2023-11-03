package pl.app.property.property.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.property.dto.PropertyBaseDto;
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
            "PropertyBaseDto", PropertyBaseDto.class,
            "PropertyDto", PropertyDto.class
    );
    public final Class<PropertyBaseDto> defaultDtoClass = PropertyBaseDto.class;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> dtoMappers = new HashMap<>();
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        dtoMappers.put(new AbstractMap.SimpleEntry<>(PropertyEntity.class, PropertyBaseDto.class),
                (PropertyEntity e) -> modelMapper.map(e, PropertyBaseDto.class));
        dtoMappers.put(new AbstractMap.SimpleEntry<>(PropertyEntity.class, PropertyDto.class),
                (PropertyEntity e) -> modelMapper.map(e, PropertyDto.class));
    }

    @Override
    public List<UUID> fetchIdAll() {
        return repository.findIdAll();
    }
}
