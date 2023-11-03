package pl.app.property.reservation.application.port.out;

import pl.app.property.reservation.application.domain.model.Reservation;

import java.util.List;
import java.util.UUID;

public interface LoadReservationsPort {
    Reservation loadReservation(UUID reservationId);

    List<UUID> loadPendingReservationIdsCreatedDayBefore(UUID propertyId);

    List<UUID> loadConfirmedReservationIds(UUID propertyId);
}
