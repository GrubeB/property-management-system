package pl.app.property.guest.service;


import pl.app.common.core.service.SimpleCommandService;
import pl.app.property.guest.model.GuestEntity;

import java.util.UUID;

public interface GuestService extends
        SimpleCommandService.CreatableWithParent<UUID, GuestEntity>,
        SimpleCommandService.Updatable<UUID, GuestEntity>,
        SimpleCommandService.DeletableById<UUID, GuestEntity> {
}
