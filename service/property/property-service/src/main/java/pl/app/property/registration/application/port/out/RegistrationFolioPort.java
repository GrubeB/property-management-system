package pl.app.property.registration.application.port.out;

import pl.app.property.registration.application.domain.model.Registration;
import pl.app.property.registration.application.domain.model.RegistrationBooking;
import pl.app.property.registration.application.port.in.CreateRegistrationFromReservationCommand;

import java.util.List;
import java.util.UUID;

public interface RegistrationFolioPort {
    UUID createRegistrationFolio(UUID registrationId,UUID propertyId, List<UUID> partyIds);
    void addPartyFolio(UUID registrationFolioId, List<UUID> partyIds);
    void removePartyFolio(UUID registrationFolioId, List<UUID> partyIds);

    UUID createRegistrationFolio(UUID registrationId, UUID propertyId, UUID partyId, CreateRegistrationFromReservationCommand.Folio folio);
    void addChargeToPartyFolioForBookings(Registration registration,RegistrationBooking registrationBooking);
    void removeChargeFromPartyFolioForBookings(Registration registration, RegistrationBooking booking);
    void changeChargeOnPartyFolioForBooking(Registration registration, RegistrationBooking booking);

    void refund(UUID registrationFolioId);

    boolean isRegistrationFolioPaid(UUID registrationFolioId);
    boolean isPartyFolioEmpty(UUID registrationFolioId, UUID partyId);
}
