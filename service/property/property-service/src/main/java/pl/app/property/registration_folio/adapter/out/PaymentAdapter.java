package pl.app.property.registration_folio.adapter.out;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.config.payment.WebsiteBankAccountService;
import pl.app.property.payment.application.domain.model.PaymentDomainObjectType;
import pl.app.property.payment.application.port.in.CreatePaymentCommand;
import pl.app.property.payment.application.port.in.CreatePaymentUseCase;
import pl.app.property.registration_folio.application.port.out.PaymentPort;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentAdapter implements
        PaymentPort {
    private final CreatePaymentUseCase createPaymentUseCase;
    private final WebsiteBankAccountService websiteBankAccountProvider;

    @Override
    public UUID createPayment(UUID propertyId, UUID folioId, String name, UUID guestId, BigDecimal amount, String currency) {
        CreatePaymentCommand command = new CreatePaymentCommand(propertyId, guestId, websiteBankAccountProvider.getWebsiteAccountId(),
                Collections.singletonList(new CreatePaymentCommand.PaymentOrder(name, amount, currency, folioId, PaymentDomainObjectType.REGISTRATION_PARTY_FOLIO))
        );
        return createPaymentUseCase.createPayment(command);
    }
}
