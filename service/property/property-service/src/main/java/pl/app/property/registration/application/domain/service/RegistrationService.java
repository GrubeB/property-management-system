package pl.app.property.registration.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.registration.application.domain.exception.RegistrationException;
import pl.app.property.registration.application.domain.model.Registration;
import pl.app.property.registration.application.domain.model.RegistrationGuest;
import pl.app.property.registration.application.domain.model.RegistrationParty;
import pl.app.property.registration.application.port.in.*;
import pl.app.property.registration.application.port.out.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class RegistrationService implements
        CancelRegistrationUseCase,
        FinishRegistrationUseCase,
        ConfirmRegistrationUseCase,
        CreateRegistrationFromReservationUseCase,
        CreateRegistrationUseCase {
    private final LoadRegistrationPort loadRegistrationPort;
    private final SaveRegistrationPort saveRegistrationPort;
    private final RegistrationFolioPort registrationFolioPort;
    private final AccommodationTypeAvailabilityPort accommodationTypeAvailabilityPort;
    private final AccommodationTypeReservationPort accommodationTypeReservationPort;

    @Override
    public UUID createRegistration(CreateRegistrationCommand command) {
        Registration registration = new Registration(command.getPropertyId(), command.getRegistrationSource());
        command.getParties().forEach(p -> registration.createParty(getGuestFromIds(p.getGuestIds())));
        command.getBookings().forEach(bookingCommand -> registration.addBooking(
                bookingCommand.getAccommodationTypeId(),
                bookingCommand.getStartDate(),
                bookingCommand.getEndDate(),
                getGuestFromIds(bookingCommand.getGuestIds())
        ));
        UUID registrationId = saveRegistrationPort.saveRegistration(registration);
        createNewRegistrationFolio(registration);
        return registrationId;
    }

    @Override
    public UUID createRegistration(CreateRegistrationFromReservationCommand command) {
        Registration registration = new Registration(command.getPropertyId(), command.getSource());
        RegistrationParty party = registration.createParty(getGuestFromIds(command.getParty().getGuestIds()));
        command.getBookings().forEach(bookingCommand -> registration.addBooking(
                bookingCommand.getAccommodationTypeId(),
                bookingCommand.getStartDate(),
                bookingCommand.getEndDate(),
                bookingCommand.getAccommodationTypeReservationId(),
                bookingCommand.getChargeIds(),
                new ArrayList<>()
        ));
        createRegistrationFolio(registration, party, command.getFolio());
        return saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void confirmRegistration(ConfirmRegistrationCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationMayBeConfirmed();
        verifyAccommodationTypeAvailability(registration);
        reserveBooking(registration);
        addChargesToFolio(registration);
        registration.confirmRegistration();
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void cancelRegistration(CancelRegistrationCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationMayBeCanceled();
        releaseAllBookings(registration);
        refundPayments(registration);
        registration.cancelRegistration();
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void finishRegistration(FinishRegistrationCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationMayBeFinished();
        verifyIfRegistrationFolioIsPaid(registration);
        registration.finishRegistration();
        saveRegistrationPort.saveRegistration(registration);
    }

    private void verifyIfRegistrationFolioIsPaid(Registration registration) {
        if (!registrationFolioPort.isFolioPaid(registration.getRegistrationFolioId())) {
            throw new RegistrationException.RegistrationWrongStatedException("Registration folio must be paid");
        }
    }

    private void verifyAccommodationTypeAvailability(Registration registration) {
        if (!accommodationTypeAvailabilityPort.isAccommodationTypeAvailable(registration)) {
            throw new RegistrationException.NoAccommodationAvailableException();
        }
    }

    private void releaseAllBookings(Registration registration) {
        registration.getRegistrationBookings()
                .forEach(accommodationTypeReservationPort::releaseAccommodationTypeReservation);
    }

    private void reserveBooking(Registration registration) {
        registration.getRegistrationBookings()
                .stream().filter(b -> Objects.isNull(b.getAccommodationTypeReservationId()))
                .forEach(booking -> {
                    UUID accommodationTypeReservationId = accommodationTypeReservationPort.reserveAccommodationType(booking);
                    booking.setAccommodationTypeReservationId(accommodationTypeReservationId);
                });
    }

    private void refundPayments(Registration registration) {
        registrationFolioPort.refund(registration.getRegistrationFolioId());
    }

    private void addChargesToFolio(Registration registration) {
        registrationFolioPort.addChargeToFolioForBooking(registration);
    }

    private List<RegistrationGuest> getGuestFromIds(List<UUID> guestIds) {
        return guestIds.stream().map(RegistrationGuest::new).toList();
    }

    private void createNewRegistrationFolio(Registration registration) {
        UUID registrationFolio = registrationFolioPort.createNewRegistrationFolio(registration.getRegistrationId(), registration.getParties().stream().map(RegistrationParty::getPartyId).toList());
        registration.setRegistrationFolioId(registrationFolio);
    }

    private void createRegistrationFolio(Registration registration, RegistrationParty party, CreateRegistrationFromReservationCommand.Folio folio) {
        UUID registrationFolioId = registrationFolioPort.createRegistrationFolio(party.getPartyId(), folio);
        registration.setRegistrationFolioId(registrationFolioId);
    }
}
