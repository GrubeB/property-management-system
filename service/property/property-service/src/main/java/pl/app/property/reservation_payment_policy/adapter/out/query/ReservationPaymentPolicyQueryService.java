package pl.app.property.reservation_payment_policy.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.model.ReservationPaymentPolicyEntity;

import java.util.UUID;


public interface ReservationPaymentPolicyQueryService extends
        QueryService.Fetchable<UUID, ReservationPaymentPolicyEntity> {
}
