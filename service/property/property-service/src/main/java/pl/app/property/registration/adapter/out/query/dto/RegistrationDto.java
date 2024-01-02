package pl.app.property.registration.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.registration.application.domain.model.RegistrationSource;
import pl.app.property.registration.application.domain.model.RegistrationStatus;
import pl.app.property.registration_folio.adapter.out.query.dto.RegistrationFolioDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto implements Serializable {
    private UUID registrationId;
    private RegistrationStatus status;
    private RegistrationSource source;
    private RegistrationFolioDto registrationFolio;
    private Set<RegistrationPartyDto> parties;
    private Set<RegistrationBookingDto> bookings;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegistrationPartyDto implements Serializable {
        private UUID partyId;
        private Set<BaseDto> guests;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegistrationBookingDto implements Serializable {
        private UUID bookingId;
        private LocalDate startDate;
        private LocalDate endDate;
        private UUID accommodationTypeId;
        private UUID accommodationTypeReservationId;
        private Set<BaseDto> guests;
        private Boolean isChargeAdded;
    }
}
