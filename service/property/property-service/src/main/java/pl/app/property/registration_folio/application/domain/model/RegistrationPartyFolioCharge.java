package pl.app.property.registration_folio.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegistrationPartyFolioCharge {
    private UUID chargeId;
    private UUID objectId;
    private RegistrationPartyFolioChargeType type;
    private String name;
    private BigDecimal amount;
    private String current;
    private Instant date;

    public RegistrationPartyFolioCharge(UUID objectId, RegistrationPartyFolioChargeType type, String name, BigDecimal amount, String current) {
        this.chargeId = UUID.randomUUID();
        this.objectId = objectId;
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.current = current;
        this.date = Instant.now();
    }

    public RegistrationPartyFolioCharge(UUID objectId, RegistrationPartyFolioChargeType type, String name, BigDecimal amount, String current, Instant date) {
        this.chargeId = UUID.randomUUID();
        this.objectId = objectId;
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.current = current;
        this.date = date;
    }
}
