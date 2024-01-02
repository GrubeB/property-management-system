package pl.app.property.reservation_payment_policy.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicyType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationPaymentPolicyDto implements Serializable {
    private UUID policyId;
    private ReservationPaymentPolicyType type;
    private BigDecimal fixedValue;
    private Integer numberOfDaysBeforeRegistration;
}
