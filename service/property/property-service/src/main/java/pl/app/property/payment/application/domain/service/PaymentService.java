package pl.app.property.payment.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.payment.application.domain.model.Payment;
import pl.app.property.payment.application.domain.model.PaymentOrder;
import pl.app.property.payment.application.port.in.CreatePaymentCommand;
import pl.app.property.payment.application.port.in.CreatePaymentUseCase;
import pl.app.property.payment.application.port.out.LoadPaymentPort;
import pl.app.property.payment.application.port.out.SavePaymentPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class PaymentService implements
        CreatePaymentUseCase {
    private final LoadPaymentPort loadPaymentPort;
    private final SavePaymentPort savePaymentPort;

    @Override
    public UUID createPayment(CreatePaymentCommand command) {
        List<PaymentOrder> orders = command.getOrders().stream()
                .map(oc -> new PaymentOrder(oc.getAmount(), oc.getName(), oc.getCurrency(), oc.getDomainObjectId(), oc.getDomainObjectType()))
                .collect(Collectors.toList());
        Payment newPayment = new Payment(command.getPropertyId(), command.getBuyerId(), command.getSellerId(), orders);
        return savePaymentPort.savePayment(newPayment);
    }
}
