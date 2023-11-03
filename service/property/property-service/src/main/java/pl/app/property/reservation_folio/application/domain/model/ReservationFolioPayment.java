package pl.app.property.reservation_folio.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReservationFolioPayment {
    private UUID paymentId;
    private UUID guestId;
    private BigDecimal amount;
    private String current;
    private Instant date;

    public ReservationFolioPayment(UUID guestId, BigDecimal amount, String current) {
        this.paymentId = UUID.randomUUID();
        this.guestId = guestId;
        this.amount = amount;
        this.current = current;
        this.date = Instant.now();
    }
}
