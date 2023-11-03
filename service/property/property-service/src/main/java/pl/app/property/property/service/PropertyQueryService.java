package pl.app.property.property.service;

import pl.app.common.core.service.QueryService;
import pl.app.property.property.model.PropertyEntity;

import java.util.List;
import java.util.UUID;

public interface PropertyQueryService extends QueryService.FullFetchable<UUID, PropertyEntity> {
    List<UUID> fetchIdAll();
}
