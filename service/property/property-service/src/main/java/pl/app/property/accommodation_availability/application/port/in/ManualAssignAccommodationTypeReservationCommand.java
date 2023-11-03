package pl.app.property.accommodation_availability.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManualAssignAccommodationTypeReservationCommand implements Serializable {
    private UUID accommodationTypeReservationId;
    private Set<AccommodationReservation> accommodationReservations;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccommodationReservation {
        private UUID accommodationId;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}