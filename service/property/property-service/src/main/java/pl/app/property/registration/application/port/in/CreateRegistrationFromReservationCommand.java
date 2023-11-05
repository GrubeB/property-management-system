package pl.app.property.registration.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.registration.application.domain.model.RegistrationSource;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioChargeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegistrationFromReservationCommand implements Serializable {
    private UUID propertyId;
    private RegistrationSource source;
    private Party party;
    private List<Booking> bookings;
    private Folio folio;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Folio {
        private List<Payment> payments;
        private List<Charge> charges;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payment {
        private UUID paymentId;
        private UUID guestId;
        private BigDecimal amount;
        private String current;
        private Instant date;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Charge {
        private UUID chargeId;
        private RegistrationPartyFolioChargeType type;
        private String name;
        private BigDecimal amount;
        private String current;
        private Instant date;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Booking {
        private LocalDate startDate;
        private LocalDate endDate;
        private UUID accommodationTypeId;
        private UUID accommodationTypeReservationId;
        private List<UUID> chargeIds;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Party {
        private List<UUID> guestIds;
    }
}