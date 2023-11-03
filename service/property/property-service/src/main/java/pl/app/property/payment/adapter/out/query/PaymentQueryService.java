package pl.app.property.payment.adapter.out.query;

import pl.app.common.core.service.QueryService;
import pl.app.property.payment.adapter.out.persistence.model.PaymentEntity;

import java.util.UUID;

public interface PaymentQueryService extends QueryService.Fetchable<UUID, PaymentEntity> {

}
