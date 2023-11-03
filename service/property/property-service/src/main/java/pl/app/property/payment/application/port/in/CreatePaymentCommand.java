package pl.app.property.payment.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.payment.application.domain.model.PaymentDomainObjectType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentCommand {
    private UUID propertyId;
    private UUID buyerId;
    private UUID sellerId;
    private List<PaymentOrder> orders;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentOrder {
        private String name;
        private BigDecimal amount;
        private String currency;
        private UUID domainObjectId;
        private PaymentDomainObjectType domainObjectType;
    }
}
