package pl.app.property.user.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.user.adapter.out.persistence.model.UserEntity;

import java.util.UUID;


public interface UserQueryService extends
        QueryService.FullFetchable<UUID, UserEntity> {
}
