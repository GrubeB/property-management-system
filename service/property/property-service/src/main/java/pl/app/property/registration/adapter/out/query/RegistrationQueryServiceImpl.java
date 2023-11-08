package pl.app.property.registration.adapter.out.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationEntity;
import pl.app.property.registration.adapter.out.persistence.repository.RegistrationRepository;
import pl.app.property.registration.adapter.out.query.dto.RegistrationDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class RegistrationQueryServiceImpl implements RegistrationQueryService {
    private final RegistrationRepository repository;
    private final RegistrationRepository specificationRepository;

    private final Map<String, Class<?>> dtoClasses = Map.of(
            "BaseDto", BaseDto.class,
            "RegistrationDto", RegistrationDto.class
    );
    public final Class<RegistrationDto> defaultDtoClass = RegistrationDto.class;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> dtoMappers = new HashMap<>();
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        dtoMappers.put(new AbstractMap.SimpleEntry<>(RegistrationEntity.class, BaseDto.class),
                (RegistrationEntity e) -> modelMapper.map(e, BaseDto.class));
        dtoMappers.put(new AbstractMap.SimpleEntry<>(RegistrationEntity.class, RegistrationDto.class),
                (RegistrationEntity e) -> modelMapper.map(e, RegistrationDto.class));
    }
}
