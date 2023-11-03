package pl.app.property.reservation_folio.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioEntity;

import java.util.UUID;


public interface ReservationFolioQueryService extends
        QueryService.Fetchable<UUID, ReservationFolioEntity> {
}
