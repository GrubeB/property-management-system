package pl.app.property.property.service;

import pl.app.common.core.service.CommandService;
import pl.app.property.property.dto.PropertyDto;
import pl.app.property.property.model.PropertyEntity;

import java.util.UUID;

public interface PropertyService extends
        CommandService.Creatable.DtoCreatableWithParent<UUID, PropertyEntity, PropertyDto, PropertyDto, UUID>,
        CommandService.Updatable.DtoUpdatableWithParent<UUID, PropertyEntity, PropertyDto, PropertyDto, UUID>,
        CommandService.Deletable.SimpleDeletable<UUID, PropertyEntity> {
}
