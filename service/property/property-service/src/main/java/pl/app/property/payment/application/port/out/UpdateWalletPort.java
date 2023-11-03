package pl.app.property.payment.application.port.out;


import pl.app.property.payment.application.domain.model.Payment;
import pl.app.property.payment.application.domain.model.PaymentOrder;

public interface UpdateWalletPort {
    void updateWallet(Payment payment, PaymentOrder paymentOrder);
}
