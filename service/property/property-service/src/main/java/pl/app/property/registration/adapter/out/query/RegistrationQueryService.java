package pl.app.property.registration.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationEntity;

import java.util.UUID;

public interface RegistrationQueryService extends QueryService.DtoFetchable<UUID, RegistrationEntity> {

}
