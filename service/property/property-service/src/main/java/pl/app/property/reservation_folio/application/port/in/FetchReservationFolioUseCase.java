package pl.app.property.reservation_folio.application.port.in;

import pl.app.property.reservation_folio.application.domain.model.ReservationFolio;

public interface FetchReservationFolioUseCase {
    ReservationFolio fetch(FetchReservationFolioCommand command);
}
