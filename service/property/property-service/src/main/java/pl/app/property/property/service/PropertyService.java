package pl.app.property.property.service;

import pl.app.common.core.service.SimpleCommandService;
import pl.app.property.property.model.PropertyEntity;

import java.util.UUID;

public interface PropertyService extends
        SimpleCommandService.CreatableWithParent<UUID, PropertyEntity>,
        SimpleCommandService.Updatable<UUID, PropertyEntity>,
        SimpleCommandService.DeletableById<UUID, PropertyEntity> {
}
