package pl.app.common.core.service.mapper;

import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

public interface MergeMapper<ENTITY> {
    @Named("mergeEntityWithAnotherEntity")
    ENTITY mergeEntityWithAnotherEntity(@MappingTarget ENTITY target, ENTITY source);
}
