package pl.app.property.accommodation_type.service;


import pl.app.common.core.service.CommandService;
import pl.app.property.accommodation_type.dto.AccommodationTypeCreateDto;
import pl.app.property.accommodation_type.dto.AccommodationTypeDto;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;

import java.util.UUID;

public interface AccommodationTypeService extends
        CommandService.Creatable.DtoCreatableWithParent<UUID, AccommodationTypeEntity, AccommodationTypeCreateDto, AccommodationTypeDto, UUID>,
        CommandService.Updatable.DtoUpdatableWithParent<UUID, AccommodationTypeEntity, AccommodationTypeDto, AccommodationTypeDto, UUID>,
        CommandService.Deletable.SimpleDeletable<UUID, AccommodationTypeEntity> {
}
