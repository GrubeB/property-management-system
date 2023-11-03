package pl.app.property.reservation_folio.application.port.out;


import java.util.UUID;

public interface ReservationMailPort {
    void sendMail(UUID paymentId, UUID guestId, UUID propertyId);
}
