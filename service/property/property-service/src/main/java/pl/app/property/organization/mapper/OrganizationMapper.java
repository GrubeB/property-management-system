package pl.app.property.organization.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.organization.dto.OrganizationDto;
import pl.app.property.organization.model.OrganizationEntity;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class OrganizationMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(OrganizationEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(OrganizationEntity.class, OrganizationDto.class, e -> modelMapper.map(e, OrganizationDto.class));
        addMapper(OrganizationDto.class, OrganizationEntity.class, e -> modelMapper.map(e, OrganizationEntity.class));
    }
}
