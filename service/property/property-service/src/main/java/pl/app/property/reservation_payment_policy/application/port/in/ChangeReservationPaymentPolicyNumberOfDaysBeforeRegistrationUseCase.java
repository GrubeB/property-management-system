package pl.app.property.reservation_payment_policy.application.port.in;

public interface ChangeReservationPaymentPolicyNumberOfDaysBeforeRegistrationUseCase {
    void changeNumberOfDays(ChangeReservationPaymentPolicyNumberOfDaysBeforeRegistrationCommand command);
}
