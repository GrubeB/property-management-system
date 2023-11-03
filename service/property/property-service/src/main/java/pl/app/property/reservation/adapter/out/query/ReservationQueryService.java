package pl.app.property.reservation.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationEntity;

import java.util.UUID;


public interface ReservationQueryService extends QueryService.Fetchable<UUID, ReservationEntity> {
}
