package pl.app.property.reservation.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.reservation.application.domain.model.ReservationSource;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationCommand implements Serializable {
    private UUID propertyId;
    private List<Booking> bookings;
    private UUID mainGuestId;
    private List<UUID> anotherGuestIds;
    private ReservationSource reservationSource;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Booking {
        private UUID accommodationTypeId;
        private Integer numberOfAccommodations;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer numberOfAdults;
        private Integer numberOfChildren;
    }
}