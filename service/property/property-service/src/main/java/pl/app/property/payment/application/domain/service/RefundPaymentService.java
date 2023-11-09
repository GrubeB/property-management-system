package pl.app.property.payment.application.domain.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.app.property.payment.application.domain.model.Payment;
import pl.app.property.payment.application.domain.model.StripePayment;
import pl.app.property.payment.application.port.in.RefundPaymentCommand;
import pl.app.property.payment.application.port.in.RefundPaymentUseCase;
import pl.app.property.payment.application.port.out.LoadStripePaymentPort;
import pl.app.property.payment.application.port.out.SaveStripePaymentPort;
import pl.app.property.registration_folio.application.port.in.AddPaymentToPartyFolioCommand;
import pl.app.property.registration_folio.application.port.in.AddPaymentToPartyFolioUseCase;
import pl.app.property.reservation_folio.application.port.in.AddPaymentCommand;
import pl.app.property.reservation_folio.application.port.in.AddPaymentUseCase;

import java.math.BigDecimal;

@Service
class RefundPaymentService implements
        RefundPaymentUseCase {
    private final LoadStripePaymentPort loadStripePaymentPort;
    private final SaveStripePaymentPort saveStripePaymentPort;

    private final AddPaymentUseCase addPaymentUseCase;
    private final AddPaymentToPartyFolioUseCase addPaymentToPartyFolioUseCase;

    public RefundPaymentService(@Lazy LoadStripePaymentPort loadStripePaymentPort,
                                @Lazy SaveStripePaymentPort saveStripePaymentPort,
                                @Lazy AddPaymentUseCase addPaymentUseCase,
                                @Lazy AddPaymentToPartyFolioUseCase addPaymentToPartyFolioUseCase) {
        this.loadStripePaymentPort = loadStripePaymentPort;
        this.saveStripePaymentPort = saveStripePaymentPort;
        this.addPaymentUseCase = addPaymentUseCase;
        this.addPaymentToPartyFolioUseCase = addPaymentToPartyFolioUseCase;
    }

    @Override
    public void refundPayment(RefundPaymentCommand command) {
        command.getPaymentIds().forEach(paymentId -> {
            StripePayment stripePayment = loadStripePaymentPort.loadStripePayment(paymentId);
            Payment payment = stripePayment.getPayment();
            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(stripePayment.getPaymentIntent())
                    .build();
            try {
                Refund refund = Refund.create(params);
                payment.markAllPaymentOrdersAsRefunded();
                payment.getPaymentOrders().forEach(order -> {
                    switch (order.getDomainObjectType()) {
                        case REGISTRATION_PARTY_FOLIO -> {
                            AddPaymentToPartyFolioCommand addPaymentToPartyFolioCommand = new AddPaymentToPartyFolioCommand(
                                    order.getDomainObjectId(),
                                    payment.getBuyerId(),
                                    BigDecimal.ZERO.subtract(order.getAmount()),
                                    order.getCurrency());
                            addPaymentToPartyFolioUseCase.addPayment(addPaymentToPartyFolioCommand);
                        }
                        case RESERVATION_FOLIO -> {
                            AddPaymentCommand addPaymentCommand = new AddPaymentCommand(
                                    order.getDomainObjectId(),
                                    payment.getBuyerId(),
                                    BigDecimal.ZERO.subtract(order.getAmount()),
                                    order.getCurrency()
                            );
                            addPaymentUseCase.addPayment(addPaymentCommand);
                        }
                    }
                });
                saveStripePaymentPort.savePayment(stripePayment);
            } catch (StripeException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
