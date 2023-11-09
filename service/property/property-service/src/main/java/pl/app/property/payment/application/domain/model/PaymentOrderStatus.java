package pl.app.property.payment.application.domain.model;

public enum PaymentOrderStatus {
    NOT_STARTED,
    EXECUTING,
    SUCCESS,
    FAILED,
    REFUNDED
}
