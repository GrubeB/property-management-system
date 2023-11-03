package pl.app.property.payment.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.property.payment.application.port.out.UpdateLedgerPort;
import pl.app.property.payment.application.port.out.UpdateWalletPort;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Payment {
    private UUID paymentId;
    private UUID buyerId;
    private UUID sellerId;
    private Boolean isPaymentDone;
    private List<PaymentOrder> paymentOrders;
    private UUID propertyId;

    public Payment(UUID propertyId, UUID buyerId, UUID sellerId, List<PaymentOrder> orders) {
        this.paymentId = UUID.randomUUID();
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.isPaymentDone = false;
        this.paymentOrders = orders;
        this.propertyId = propertyId;
    }

    public void markAllPaymentOrdersAsPaid(UpdateLedgerPort updateLedgerPort, UpdateWalletPort updateWalletPort) {
        this.isPaymentDone = true;
        this.paymentOrders.forEach(order -> {
            order.markAsSucceeded();
            updateLedgerPort.updateLedger(this, order);
            updateWalletPort.updateWallet(this, order);
        });
    }

    public void markAllPaymentOrdersAsExecuting() {
        this.paymentOrders.forEach(PaymentOrder::markAsExecuting);
    }

    public void markAllPaymentOrdersAsFailed() {
        this.paymentOrders.forEach(PaymentOrder::markAsFailed);
    }
}
