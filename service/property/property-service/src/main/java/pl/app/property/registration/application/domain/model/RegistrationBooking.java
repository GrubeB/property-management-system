package pl.app.property.registration.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegistrationBooking {
    private UUID bookingId;
    private LocalDate start;
    private LocalDate end;
    private UUID accommodationTypeId;
    private List<RegistrationGuest> guests;
    private UUID accommodationTypeReservationId;
    private Boolean isChargeAdded;


    public RegistrationBooking(LocalDate start, LocalDate end, UUID accommodationTypeId, List<RegistrationGuest> guests) {
        this.bookingId = UUID.randomUUID();
        this.start = start;
        this.end = end;
        this.accommodationTypeId = accommodationTypeId;
        this.guests = guests;
        this.accommodationTypeReservationId = null;
        this.isChargeAdded = false;
    }

    public RegistrationBooking(LocalDate start, LocalDate end, UUID accommodationTypeId, UUID accommodationTypeReservationId, List<RegistrationGuest> guests) {
        this.bookingId = UUID.randomUUID();
        this.start = start;
        this.end = end;
        this.accommodationTypeId = accommodationTypeId;
        this.guests = guests;
        this.accommodationTypeReservationId = accommodationTypeReservationId;
        this.isChargeAdded = false;
    }

    public void addGuest(RegistrationGuest guest) {
        this.guests.add(guest);
    }

    public void removeGuest(RegistrationGuest guestToRemove) {
        this.guests.removeIf(guest -> Objects.equals(guest.getGuestId(), guestToRemove.getGuestId()));
    }

    public boolean containsGuest(RegistrationGuest targetGuest) {
        return this.guests.stream().anyMatch(guest -> Objects.equals(guest.getGuestId(), targetGuest.getGuestId()));
    }

    public void setAccommodationTypeReservationId(UUID accommodationTypeReservationId) {
        this.accommodationTypeReservationId = accommodationTypeReservationId;
    }

    public boolean isReserved() {
        return this.accommodationTypeReservationId != null;
    }

    public void setChargeAdded(Boolean isChargeAdded) {
        this.isChargeAdded = isChargeAdded;
    }
}
