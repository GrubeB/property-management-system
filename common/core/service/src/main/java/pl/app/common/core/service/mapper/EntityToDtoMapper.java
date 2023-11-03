package pl.app.common.core.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface EntityToDtoMapper<ENTITY, DTO> {
    DTO mapEntityToDto(ENTITY entity);

    default Set<DTO> mapEntitySetToDtoSet(Set<ENTITY> entitySet){
        return entitySet.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toSet());
    }

    default List<DTO> mapEntityListToDtoList(List<ENTITY> entityList){
        return entityList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
