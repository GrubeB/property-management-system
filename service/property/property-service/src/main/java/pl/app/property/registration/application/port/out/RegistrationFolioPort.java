package pl.app.property.registration.application.port.out;

import pl.app.property.registration.application.domain.model.Registration;
import pl.app.property.registration.application.domain.model.RegistrationBooking;
import pl.app.property.registration.application.port.in.CreateRegistrationFromReservationCommand;

import java.util.List;
import java.util.UUID;

public interface RegistrationFolioPort {
    UUID createNewRegistrationFolio(UUID registrationId, List<UUID> partyIds);

    UUID createRegistrationFolio(UUID partyId, CreateRegistrationFromReservationCommand.Folio folio);
    void addChargeToPartyFolioForBookings(Registration registration,RegistrationBooking registrationBooking);
    void changeChargeOnPartyFolioForBooking(Registration registration, RegistrationBooking booking);
    boolean isRegistrationFolioPaid(UUID registrationFolioId);

    void refund(UUID registrationFolioId);
    boolean isPartyFolioEmpty(UUID registrationFolioId, UUID partyId);
}
