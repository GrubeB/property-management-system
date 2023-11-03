package pl.app.property.payment.application.port.out;


import pl.app.property.payment.application.domain.model.Payment;
import pl.app.property.payment.application.domain.model.PaymentOrder;

public interface UpdateLedgerPort {
    void updateLedger(Payment payment, PaymentOrder paymentOrder);
}
