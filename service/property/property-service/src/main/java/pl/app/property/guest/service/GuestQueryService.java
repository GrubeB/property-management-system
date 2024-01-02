package pl.app.property.guest.service;


import pl.app.common.core.service.QueryService;
import pl.app.property.guest.model.GuestEntity;

import java.util.UUID;

public interface GuestQueryService extends
        QueryService.Full<UUID, GuestEntity> {
}
