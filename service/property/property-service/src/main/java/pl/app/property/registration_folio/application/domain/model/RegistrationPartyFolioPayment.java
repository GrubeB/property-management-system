package pl.app.property.registration_folio.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegistrationPartyFolioPayment {
    private UUID paymentId;
    private UUID guestId;
    private BigDecimal amount;
    private String current;
    private Instant date;


    public RegistrationPartyFolioPayment(UUID guestId, BigDecimal amount, String current) {
        this.paymentId = UUID.randomUUID();
        this.guestId = guestId;
        this.amount = amount;
        this.current = current;
        this.date = Instant.now();
    }

    public RegistrationPartyFolioPayment(UUID guestId, BigDecimal amount, String current, Instant date) {
        this.paymentId = UUID.randomUUID();
        this.guestId = guestId;
        this.amount = amount;
        this.current = current;
        this.date = date;
    }
}
