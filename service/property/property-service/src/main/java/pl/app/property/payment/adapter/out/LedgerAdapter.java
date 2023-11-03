package pl.app.property.payment.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.payment.adapter.out.persistence.model.LedgerEntity;
import pl.app.property.payment.adapter.out.persistence.repository.LedgerRepository;
import pl.app.property.payment.application.domain.model.Payment;
import pl.app.property.payment.application.domain.model.PaymentOrder;
import pl.app.property.payment.application.port.out.UpdateLedgerPort;
import pl.app.property.property.service.PropertyQueryService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LedgerAdapter implements
        UpdateLedgerPort {
    private final LedgerRepository ledgerRepository;
    private final PropertyQueryService propertyQueryService;

    @Override
    public void updateLedger(Payment payment, PaymentOrder paymentOrder) {
        LedgerEntity buyerLedger = LedgerEntity.builder()
                .property(propertyQueryService.fetchById(payment.getPropertyId()))
                .accountId(payment.getBuyerId())
                .debit(paymentOrder.getAmount())
                .credit(BigDecimal.ZERO)
                .date(Instant.now())
                .build();
        LedgerEntity sellerLedger = LedgerEntity.builder()
                .property(propertyQueryService.fetchById(payment.getPropertyId()))
                .accountId(payment.getSellerId())
                .debit(BigDecimal.ZERO)
                .credit(paymentOrder.getAmount())
                .date(Instant.now())
                .build();
        ledgerRepository.saveAll(List.of(buyerLedger, sellerLedger));
    }
}
