package pl.app.property.payment.application.port.in;

public interface RefundPaymentUseCase {
    void refundPayment(RefundPaymentCommand command);
}
