package pl.app.property.reservation.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
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

    public ReservationBooking(LocalDate start, LocalDate end, UUID accommodationTypeId, Integer numberOfAdults, Integer numberOfChildren) {
        this.reservationBookingId = UUID.randomUUID();
        this.start = start;
        this.end = end;
        this.accommodationTypeId = accommodationTypeId;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.accommodationTypeReservationId = null;
    }

    public void setAccommodationTypeReservationId(UUID accommodationTypeReservationId) {
        this.accommodationTypeReservationId = accommodationTypeReservationId;
    }

    public boolean isReserved() {
        return this.accommodationTypeReservationId != null;
    }
}
