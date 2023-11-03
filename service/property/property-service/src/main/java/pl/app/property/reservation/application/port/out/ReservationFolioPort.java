package pl.app.property.reservation.application.port.out;

import pl.app.property.reservation.application.domain.model.Reservation;

import java.util.UUID;

public interface ReservationFolioPort {
    UUID createFolio(UUID reservationId, UUID propertyId);

    void addChargeToFolioForBooking(Reservation reservation);

    boolean isFolioPaid(UUID reservationFolioId);

    void refund(UUID reservationFolioId);
}
