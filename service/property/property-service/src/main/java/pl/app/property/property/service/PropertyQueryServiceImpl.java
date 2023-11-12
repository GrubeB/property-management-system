package pl.app.property.property.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.property.dto.PropertyDto;
import pl.app.property.property.mapper.PropertyMapper;
import pl.app.property.property.persistence.PropertyRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class PropertyQueryServiceImpl implements PropertyQueryService {
    private final PropertyRepository repository;
    private final PropertyRepository specificationRepository;
    private final PropertyMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = Map.of(
            "PropertyDto", PropertyDto.class,
            "BaseDto", BaseDto.class
    );

    @Override
    public List<UUID> fetchIdAll() {
        return repository.findIdAll();
    }
}
