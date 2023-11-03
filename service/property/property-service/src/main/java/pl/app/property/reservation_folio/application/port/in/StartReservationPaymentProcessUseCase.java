package pl.app.property.reservation_folio.application.port.in;

import java.util.UUID;

public interface StartReservationPaymentProcessUseCase {
    UUID startPaymentProcess(StartReservationPaymentProcessCommand command);
}
