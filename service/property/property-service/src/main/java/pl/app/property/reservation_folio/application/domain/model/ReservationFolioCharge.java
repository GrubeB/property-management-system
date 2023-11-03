package pl.app.property.reservation_folio.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReservationFolioCharge {
    private UUID chargeId;
    private ReservationFolioChargeType type;
    private String name;
    private BigDecimal amount;
    private String current;
    private Instant date;
    private Boolean shouldByPaidBeforeRegistration;

    public ReservationFolioCharge(ReservationFolioChargeType type, String name, BigDecimal amount, String current, Boolean shouldByPaidBeforeRegistration) {
        this.chargeId = UUID.randomUUID();
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.current = current;
        this.date = Instant.now();
        this.shouldByPaidBeforeRegistration = shouldByPaidBeforeRegistration;
    }
}
