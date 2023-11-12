package pl.app.property.guest.service;


import pl.app.common.core.service.CommandService;
import pl.app.property.guest.dto.GuestDto;
import pl.app.property.guest.model.GuestEntity;

import java.util.UUID;

public interface GuestService extends
        CommandService.Creatable.DtoCreatableWithParent<UUID, GuestEntity, GuestDto, GuestDto, UUID>,
        CommandService.Updatable.DtoUpdatableWithParent<UUID, GuestEntity, GuestDto, GuestDto, UUID>,
        CommandService.Deletable.SimpleDeletable<UUID, GuestEntity> {
}
