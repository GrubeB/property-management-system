package pl.app.property.registration_folio.application.port.out;


import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentPort {
    UUID createPayment(UUID propertyId, UUID folioId, String name, UUID guestId, BigDecimal amount, String currency);
}
