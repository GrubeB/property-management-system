package pl.app.property.reservation_folio.application.port.out;


import java.math.BigDecimal;
import java.util.UUID;

public interface ReservationPaymentPort {
    UUID createPayment(UUID propertyId, UUID reservationFolioId, String name, UUID guestId, BigDecimal amount, String currency);
}
