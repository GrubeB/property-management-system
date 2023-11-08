package pl.app.property.registration.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddBookingToRegistrationCommand implements Serializable {
    private UUID registrationId;
    private List<AccommodationTypeBooking> bookings;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccommodationTypeBooking {
        private LocalDate startDate;
        private LocalDate endDate;
        private UUID accommodationTypeId;
        private List<UUID> guestIds;
    }
}