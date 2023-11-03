package pl.app.property.payment.application.domain.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.config.domain.WebAddressService;
import pl.app.property.payment.adapter.in.PaymentController;
import pl.app.property.payment.application.domain.model.Payment;
import pl.app.property.payment.application.port.in.CreatePaymentCheckoutCommand;
import pl.app.property.payment.application.port.in.CreatePaymentCheckoutUseCase;
import pl.app.property.payment.application.port.in.PaymentCheckoutFailedUseCase;
import pl.app.property.payment.application.port.in.PaymentCheckoutSuccessUseCase;
import pl.app.property.payment.application.port.out.LoadPaymentPort;
import pl.app.property.payment.application.port.out.SavePaymentPort;
import pl.app.property.payment.application.port.out.UpdateLedgerPort;
import pl.app.property.payment.application.port.out.UpdateWalletPort;
import pl.app.property.registration_folio.application.port.in.AddPaymentToPartyFolioCommand;
import pl.app.property.registration_folio.application.port.in.AddPaymentToPartyFolioUseCase;
import pl.app.property.reservation_folio.application.port.in.AddPaymentCommand;
import pl.app.property.reservation_folio.application.port.in.AddPaymentUseCase;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class PaymentCheckoutService implements
        CreatePaymentCheckoutUseCase,
        PaymentCheckoutFailedUseCase,
        PaymentCheckoutSuccessUseCase {
    private final SavePaymentPort savePaymentPort;
    private final LoadPaymentPort loadPaymentPort;
    private final UpdateLedgerPort updateLedgerPort;
    private final UpdateWalletPort updateWalletPort;

    private final AddPaymentUseCase addPaymentUseCase;
    private final AddPaymentToPartyFolioUseCase addPaymentToPartyFolioUseCase;

    private final WebAddressService webAddressService;
    private final String secretKey = "sk_test_51NjpJyDFK0HbVqEB9TVjn3ww7RgnQEITW1WTW27I1WGfoDTkSQADvASqVPHvBalHCp0aMHvZ45bvLNiKNGjhp27Q00uURo7u7N";

    @PostConstruct
    private void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public Session createCheckoutSession(CreatePaymentCheckoutCommand command) {
        Payment payment = loadPaymentPort.loadPayment(command.getPaymentId());
        List<SessionCreateParams.LineItem> items = payment.getPaymentOrders().stream()
                .map(po -> SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(po.getCurrency())
                                        .setUnitAmount(po.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(po.getName())
                                                        .build())
                                        .build())
                        .build())
                .collect(Collectors.toList());


        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(webAddressService.getWebAddress() + PaymentController.controllerPath +
                                PaymentController.checkoutSessionSuccessPath + "/" + command.getPaymentId())
                        .setCancelUrl(webAddressService.getWebAddress() + PaymentController.controllerPath +
                                PaymentController.checkoutSessionFailedPath + "/" + command.getPaymentId())
                        .addAllLineItem(items)
                        .build();
        try {
            Session session = Session.create(params);
            payment.markAllPaymentOrdersAsExecuting();
            savePaymentPort.savePayment(payment);
            return session;
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkoutSessionFailed(UUID paymentId) {
        Payment payment = loadPaymentPort.loadPayment(paymentId);
        payment.markAllPaymentOrdersAsFailed();
        savePaymentPort.savePayment(payment);
    }

    @Override
    public void checkoutSessionSuccess(UUID paymentId) {
        Payment payment = loadPaymentPort.loadPayment(paymentId);
        payment.markAllPaymentOrdersAsPaid(updateLedgerPort, updateWalletPort);
        savePaymentPort.savePayment(payment);

        payment.getPaymentOrders().forEach(order -> {
            switch (order.getDomainObjectType()) {
                case REGISTRATION_PARTY_FOLIO -> {
                    AddPaymentToPartyFolioCommand addPaymentToPartyFolioCommand = new AddPaymentToPartyFolioCommand(
                            order.getDomainObjectId(),
                            payment.getBuyerId(),
                            order.getAmount(),
                            order.getCurrency());
                    addPaymentToPartyFolioUseCase.addPayment(addPaymentToPartyFolioCommand);
                }
                case RESERVATION_FOLIO -> {
                    AddPaymentCommand command = new AddPaymentCommand(
                            order.getDomainObjectId(),
                            payment.getBuyerId(),
                            order.getAmount(),
                            order.getCurrency()
                    );
                    addPaymentUseCase.addPayment(command);
                }
            }
        });
    }
}
