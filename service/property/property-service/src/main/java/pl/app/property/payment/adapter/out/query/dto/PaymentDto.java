package pl.app.property.payment.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.payment.application.domain.model.PaymentDomainObjectType;
import pl.app.property.payment.application.domain.model.PaymentOrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto implements Serializable {
    private UUID paymentId;
    private UUID buyerId;
    private UUID sellerId;
    private Boolean isPaymentDone;
    private Set<PaymentOrderDto> paymentOrders;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentOrderDto implements Serializable {
        private UUID paymentOrderId;
        private BigDecimal amount;
        private String current;
        private PaymentOrderStatus status;
        private String paymentName;
        private UUID domainObjectId;
        private PaymentDomainObjectType domainObjectType;
    }
}
