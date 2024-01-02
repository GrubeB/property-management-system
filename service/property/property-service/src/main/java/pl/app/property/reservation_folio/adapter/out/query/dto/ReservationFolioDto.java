package pl.app.property.reservation_folio.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolioChargeType;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicyType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationFolioDto implements Serializable {
    private UUID reservationFolioId;
    private BaseDto reservation;
    private ReservationPaymentPolicyType type;
    private Set<ReservationFolioPaymentDto> payments;
    private Set<ReservationFolioChargeDto> charges;
    private Set<UUID> globalPaymentIds;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReservationFolioPaymentDto implements Serializable {
        private UUID paymentId;
        private UUID guestId;
        private BigDecimal amount;
        private String current;
        private Instant date;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReservationFolioChargeDto implements Serializable {
        private UUID chargeId;
        private UUID objectId;
        private ReservationFolioChargeType type;
        private String name;
        private BigDecimal amount;
        private String current;
        private Instant date;
        private Boolean shouldByPaidBeforeRegistration;
    }
}
