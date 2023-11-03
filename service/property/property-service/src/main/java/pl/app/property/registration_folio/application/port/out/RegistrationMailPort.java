package pl.app.property.registration_folio.application.port.out;


import java.util.UUID;

public interface RegistrationMailPort {
    void sendMail(UUID paymentId, UUID guestId, UUID propertyId);
}
