package pl.app.property.reservation_folio.application.port.out;


import pl.app.property.reservation_folio.application.domain.model.ReservationFolio;

import java.util.UUID;

public interface SaveReservationFolioPort {
    UUID saveRegistrationFolio(ReservationFolio reservationFolio);
}
