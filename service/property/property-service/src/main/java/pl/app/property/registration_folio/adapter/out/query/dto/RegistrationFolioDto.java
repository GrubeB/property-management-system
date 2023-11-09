package pl.app.property.registration_folio.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioChargeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationFolioDto implements Serializable {
    private UUID registrationFolioId;
    private Set<PartyFolioDto> partyFolios;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PartyFolioDto implements Serializable {
        private UUID partyFolioId;
        private UUID partyId;
        private Set<PartyFolioPaymentDto> payments;
        private Set<PartyFolioChargeDto> charges;
        private Set<UUID> globalPaymentIds;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PartyFolioPaymentDto implements Serializable {
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
    public static class PartyFolioChargeDto implements Serializable {
        private UUID chargeId;
        private UUID objectId;
        private RegistrationPartyFolioChargeType type;
        private String name;
        private BigDecimal amount;
        private String current;
        private Instant date;
    }
}
