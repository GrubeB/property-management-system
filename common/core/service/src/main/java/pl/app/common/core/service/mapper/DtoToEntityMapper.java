package pl.app.common.core.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface DtoToEntityMapper<ENTITY, DTO> {
    ENTITY mapDtoToEntity(DTO dto);

    default Set<ENTITY> mapDtoSetToEntitySet(Set<DTO> dtoSet){
        return dtoSet.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toSet());
    }
    default List<ENTITY> mapDtoListToEntityList(List<DTO> dtoList){
        return dtoList.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
