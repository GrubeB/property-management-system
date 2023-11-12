package pl.app.property.registration_folio.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.registration_folio.adapter.out.persistence.model.RegistrationFolioEntity;

import java.util.UUID;

public interface RegistrationFolioQueryService extends
        QueryService.Full<UUID, RegistrationFolioEntity> {

}
