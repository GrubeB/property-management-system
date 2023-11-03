package pl.app.property.registration.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.registration.application.domain.model.RegistrationSource;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegistrationCommand implements Serializable {
    private RegistrationSource registrationSource;
    private UUID propertyId;
    private List<Party> parties;
    private List<Booking> bookings;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Booking {
        private LocalDate startDate;
        private LocalDate endDate;
        private UUID accommodationTypeId;
        private List<UUID> guestIds;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Party {
        private List<UUID> guestIds;
    }
}