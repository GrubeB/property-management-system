package pl.app.property.reservation_payment_policy.application.port.in;

public interface ChangeReservationPaymentPolicyStatusToNoneUseCase {
    void changeStatusToNone(ChangeReservationPaymentPolicyStatusToNoneCommand command);
}
