package pl.app.property.payment.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.payment.adapter.out.persistence.model.WalletEntity;
import pl.app.property.payment.adapter.out.persistence.repository.WalletRepository;
import pl.app.property.payment.application.domain.model.Payment;
import pl.app.property.payment.application.domain.model.PaymentOrder;
import pl.app.property.payment.application.port.out.UpdateWalletPort;
import pl.app.property.property.service.PropertyQueryService;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WalletAdapter implements
        UpdateWalletPort {
    private final WalletRepository walletRepository;
    private final PropertyQueryService propertyQueryService;

    @Override
    public void updateWallet(Payment payment, PaymentOrder paymentOrder) {
        WalletEntity buyerWalletEntity = walletRepository.findByAccountId(payment.getBuyerId())
                .orElse(WalletEntity.builder()
                        .property(propertyQueryService.fetchById(payment.getPropertyId()))
                        .accountId(payment.getBuyerId())
                        .amount(BigDecimal.ZERO)
                        .build());
        buyerWalletEntity.setAmount(buyerWalletEntity.getAmount().subtract(paymentOrder.getAmount()));

        WalletEntity sellerWalletEntity = walletRepository.findByAccountId(payment.getSellerId())
                .orElse(WalletEntity.builder()
                        .property(propertyQueryService.fetchById(payment.getPropertyId()))
                        .accountId(payment.getSellerId())
                        .amount(BigDecimal.ZERO)
                        .build());
        buyerWalletEntity.setAmount(buyerWalletEntity.getAmount().add(paymentOrder.getAmount()));
        walletRepository.saveAll(List.of(buyerWalletEntity, sellerWalletEntity));
    }
}
