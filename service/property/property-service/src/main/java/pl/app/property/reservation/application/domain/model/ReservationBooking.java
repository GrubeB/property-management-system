package pl.app.property.reservation.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReservationBooking {
    private UUID reservationBookingId;
    private LocalDate start;
    private LocalDate end;
    private UUID accommodationTypeId;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private UUID accommodationTypeReservationId;
    private List<UUID> chargeIds;

    public ReservationBooking(LocalDate start, LocalDate end, UUID accommodationTypeId, Integer numberOfAdults, Integer numberOfChildren) {
        this.reservationBookingId = UUID.randomUUID();
        this.start = start;
        this.end = end;
        this.accommodationTypeId = accommodationTypeId;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.accommodationTypeReservationId = null;
        this.chargeIds = new ArrayList<>();
    }

    public void setAccommodationTypeReservationId(UUID accommodationTypeReservationId) {
        this.accommodationTypeReservationId = accommodationTypeReservationId;
    }

    public void setNumberOfPeople(Integer numberOfAdults, Integer numberOfChildren) {
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
    }

    public void addCharge(UUID chargeId) {
        this.chargeIds.add(chargeId);
    }

    public void removeAllCharge() {
        this.chargeIds.clear();
    }
}
