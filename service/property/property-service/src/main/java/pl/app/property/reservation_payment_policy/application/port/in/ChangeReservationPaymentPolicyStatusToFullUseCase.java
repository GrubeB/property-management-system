package pl.app.property.reservation_payment_policy.application.port.in;

public interface ChangeReservationPaymentPolicyStatusToFullUseCase {
    void changeStatusToFull(ChangeReservationPaymentPolicyStatusToFullCommand command);
}
