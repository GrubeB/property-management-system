package pl.app.property.reservation_folio.adapter.out;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.config.payment.WebsiteBankAccountService;
import pl.app.property.payment.application.domain.model.PaymentDomainObjectType;
import pl.app.property.payment.application.port.in.CreatePaymentCommand;
import pl.app.property.payment.application.port.in.CreatePaymentUseCase;
import pl.app.property.reservation_folio.application.port.out.ReservationPaymentPort;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationPaymentAdapter implements
        ReservationPaymentPort {
    private final CreatePaymentUseCase createPaymentUseCase;
    private final WebsiteBankAccountService websiteBankAccountProvider;

    @Override
    public UUID createPayment(UUID propertyId, UUID folioId, String name, UUID guestId, BigDecimal amount, String currency) {
        CreatePaymentCommand command = new CreatePaymentCommand(propertyId, guestId, websiteBankAccountProvider.getWebsiteAccountId(),
                Collections.singletonList(new CreatePaymentCommand.PaymentOrder(name, amount, currency, folioId,
                        PaymentDomainObjectType.RESERVATION_FOLIO))
        );
        return createPaymentUseCase.createPayment(command);
    }
}
