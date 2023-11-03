package pl.app.property.payment.adapter.out.persistence.maper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.payment.adapter.out.persistence.model.PaymentEntity;
import pl.app.property.payment.adapter.out.persistence.model.PaymentOrderEntity;
import pl.app.property.payment.application.domain.model.Payment;
import pl.app.property.payment.application.domain.model.PaymentOrder;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentMapper {
    private final PropertyQueryService propertyQueryService;

    public Payment mapToPayment(PaymentEntity entity) {
        return new Payment(
                entity.getPaymentId(),
                entity.getBuyerId(),
                entity.getSellerId(),
                entity.getIsPaymentDone(),
                entity.getPaymentOrders().stream().map(this::mapToPaymentOrder).collect(Collectors.toList()),
                entity.getProperty().getPropertyId()
        );
    }

    private PaymentOrder mapToPaymentOrder(PaymentOrderEntity entity) {
        return new PaymentOrder(
                entity.getPaymentOrderId(),
                entity.getAmount(),
                entity.getCurrent(),
                entity.getPaymentName(),
                entity.getStatus(),
                entity.getDomainObjectId(),
                entity.getDomainObjectType(),
                entity.getIsLedgerUpdated(),
                entity.getIsWalletUpdated()
        );
    }

    public PaymentEntity mapToPaymentEntity(Payment domain) {
        PaymentEntity entity = PaymentEntity.builder()
                .paymentId(domain.getPaymentId())
                .buyerId(domain.getBuyerId())
                .sellerId(domain.getSellerId())
                .isPaymentDone(domain.getIsPaymentDone())
                .paymentOrders(domain.getPaymentOrders().stream().map(this::mapToPaymentOrderEntity).collect(Collectors.toSet()))
                .property(this.mapToPropertyEntity(domain.getPropertyId()))
                .build();
        entity.getPaymentOrders().forEach(o -> o.setPayment(entity));
        return entity;
    }

    private PaymentOrderEntity mapToPaymentOrderEntity(PaymentOrder domain) {
        return PaymentOrderEntity.builder()
                .paymentOrderId(domain.getPaymentOrderId())
                .amount(domain.getAmount())
                .current(domain.getCurrency())
                .paymentName(domain.getName())
                .status(domain.getStatus())
                .domainObjectId(domain.getDomainObjectId())
                .domainObjectType(domain.getDomainObjectType())
                .isLedgerUpdated(domain.getIsLedgerUpdated())
                .isWalletUpdated(domain.getIsWalletUpdated())
                .build();
    }

    private PropertyEntity mapToPropertyEntity(UUID propertyId) {
        return propertyQueryService.fetchById(propertyId);
    }
}
