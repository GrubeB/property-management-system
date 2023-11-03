package pl.app.property.reservation_payment_policy.application.port.in;

public interface ChangeReservationPaymentPolicyStatusToFirstDayUseCase {
    void changeStatusToFirstDay(ChangeReservationPaymentPolicyStatusToFirstDayCommand command);
}
