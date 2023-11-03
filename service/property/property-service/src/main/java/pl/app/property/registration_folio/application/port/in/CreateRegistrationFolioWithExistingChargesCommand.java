package pl.app.property.registration_folio.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioChargeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegistrationFolioWithExistingChargesCommand implements Serializable {
    private List<PartyFolio> partyFolios;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartyFolio {
        private UUID partyId;
        private List<Payment> payments;
        private List<Charge> charges;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payment {
        private UUID guestId;
        private BigDecimal amount;
        private String current;
        private Instant date;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Charge {
        private RegistrationPartyFolioChargeType type;
        private String name;
        private BigDecimal amount;
        private String current;
        private Instant date;
    }
}