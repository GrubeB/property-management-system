package pl.app.property.registration_folio.adapter.out.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.registration_folio.adapter.out.persistence.model.RegistrationFolioEntity;
import pl.app.property.registration_folio.adapter.out.persistence.repository.RegistrationFolioRepository;
import pl.app.property.registration_folio.adapter.out.query.dto.RegistrationFolioDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class RegistrationFolioQueryServiceImpl implements
        RegistrationFolioQueryService {
    private final RegistrationFolioRepository repository;
    private final RegistrationFolioRepository specificationRepository;

    private final Map<String, Class<?>> dtoClasses = Map.of(
            "BaseDto", BaseDto.class,
            "RegistrationFolioDto", RegistrationFolioDto.class
    );
    public final Class<RegistrationFolioDto> defaultDtoClass = RegistrationFolioDto.class;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> dtoMappers = new HashMap<>();
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        dtoMappers.put(new AbstractMap.SimpleEntry<>(RegistrationFolioEntity.class, BaseDto.class),
                (RegistrationFolioEntity e) -> modelMapper.map(e, BaseDto.class));
        dtoMappers.put(new AbstractMap.SimpleEntry<>(RegistrationFolioEntity.class, RegistrationFolioDto.class),
                (RegistrationFolioEntity e) -> modelMapper.map(e, RegistrationFolioDto.class));
    }
}
