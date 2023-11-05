package pl.app.property.registration.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private List<UUID> chargeIds;


    public RegistrationBooking(LocalDate start, LocalDate end, UUID accommodationTypeId, List<RegistrationGuest> guests) {
        this.bookingId = UUID.randomUUID();
        this.start = start;
        this.end = end;
        this.accommodationTypeId = accommodationTypeId;
        this.guests = guests;
        this.accommodationTypeReservationId = null;
        this.chargeIds = new ArrayList<>();
    }

    public RegistrationBooking(LocalDate start, LocalDate end, UUID accommodationTypeId, UUID accommodationTypeReservationId, List<UUID> chargeIds, List<RegistrationGuest> guests) {
        this.bookingId = UUID.randomUUID();
        this.start = start;
        this.end = end;
        this.accommodationTypeId = accommodationTypeId;
        this.guests = guests;
        this.accommodationTypeReservationId = accommodationTypeReservationId;
        this.chargeIds = chargeIds;
    }

    public void addGuest(RegistrationGuest guest) {
        this.guests.add(guest);
    }

    public void removeGuest(RegistrationGuest guest) {
        this.guests.remove(guest);
    }
    public boolean containsGuest(RegistrationGuest guest) {
        return this.guests.contains(guest);
    }
    public void setAccommodationTypeReservationId(UUID accommodationTypeReservationId) {
        this.accommodationTypeReservationId = accommodationTypeReservationId;
    }

    public void addCharge(UUID chargeId) {
        this.chargeIds.add(chargeId);
    }

    public boolean hasCharges() {
        return this.chargeIds.isEmpty();
    }

    public void removeAllCharge() {
        this.chargeIds.clear();
    }
    public boolean isReserved() {
        return this.accommodationTypeReservationId != null;
    }
}
