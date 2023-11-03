package pl.app.property.accommodation_type.service;


import pl.app.common.core.service.SimpleCommandService;
import pl.app.property.accommodation_type.command.CreateAccommodationTypeCommand;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;

import java.util.UUID;

public interface AccommodationTypeService extends
        SimpleCommandService.CreatableWithParent<UUID, AccommodationTypeEntity>,
        SimpleCommandService.Updatable<UUID, AccommodationTypeEntity>,
        SimpleCommandService.DeletableById<UUID, AccommodationTypeEntity> {
    AccommodationTypeEntity create(CreateAccommodationTypeCommand command);
}
