package pl.app.property.reservation_payment_policy.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReservationPaymentPolicy {
    private UUID policyId;
    private UUID propertyId;
    private ReservationPaymentPolicyType type;
    private BigDecimal fixedValue;
    private Integer numberOfDaysBeforeRegistration;

    public ReservationPaymentPolicy(UUID propertyId) {
        this.policyId = UUID.randomUUID();
        this.propertyId = propertyId;
        this.type = ReservationPaymentPolicyType.NONE;
        this.fixedValue = BigDecimal.ZERO;
        this.numberOfDaysBeforeRegistration = 3;
    }

    public void setNumberOfDaysBeforeRegistration(Integer newNumber) {
        if (newNumber < 0 || newNumber > 1_000) {
            return;
        }
        this.numberOfDaysBeforeRegistration = newNumber;
    }

    public void changeTypeToFull() {
        this.type = ReservationPaymentPolicyType.FULL;
    }

    public void changeTypeToFirstDay() {
        this.type = ReservationPaymentPolicyType.FIRST_DAY;
    }

    public void changeTypeToFixedValue(BigDecimal fixedValue) {
        this.type = ReservationPaymentPolicyType.FIXED;
        this.fixedValue = fixedValue;
    }

    public void changeTypeToNone() {
        this.type = ReservationPaymentPolicyType.NONE;
    }
}
