package pl.app.property.payment.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class PaymentOrder {
    private UUID paymentOrderId;
    private BigDecimal amount;
    private String currency;
    private String name;
    private PaymentOrderStatus status;

    private UUID domainObjectId;
    private PaymentDomainObjectType domainObjectType;

    private Boolean isLedgerUpdated;
    private Boolean isWalletUpdated;

    public PaymentOrder(BigDecimal amount, String name, String currency, UUID domainObjectId, PaymentDomainObjectType domainObjectType) {
        this.paymentOrderId = UUID.randomUUID();
        this.name = name;
        this.amount = amount;
        this.currency = currency;
        this.status = PaymentOrderStatus.NOT_STARTED;
        this.domainObjectId = domainObjectId;
        this.domainObjectType = domainObjectType;
        this.isLedgerUpdated = false;
        this.isWalletUpdated = false;
    }

    public void markAsSucceeded() {
        this.status = PaymentOrderStatus.SUCCESS;
    }

    public void markAsExecuting() {
        this.status = PaymentOrderStatus.EXECUTING;
    }

    public void markAsFailed() {
        this.status = PaymentOrderStatus.FAILED;
    }

    public void markAsRefunded() {
        this.status = PaymentOrderStatus.REFUNDED;
    }
}
