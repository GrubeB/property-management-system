package pl.app.property.registration.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.registration.application.domain.exception.RegistrationException;
import pl.app.property.registration.application.domain.model.*;
import pl.app.property.registration.application.port.in.*;
import pl.app.property.registration.application.port.out.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class RegistrationService implements
        AddGuestToBookingUseCase,
        RemoveGuestFromBookingUseCase,
        AddBookingToRegistrationUseCase,
        RemoveBookingFromRegistrationUseCase,
        ChangeGuestPartyUseCase,
        AddGuestToPartyUseCase,
        RemoveGuestFromPartyUseCase,
        AddPartyUseCase,
        RemovePartyUseCase,
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
        command.getParties().forEach(newParty -> registration.addPartyFromGuestIds(newParty.getGuestIds()));
        command.getBookings().forEach(bookingCommand -> registration.addBooking(
                bookingCommand.getAccommodationTypeId(),
                bookingCommand.getStartDate(),
                bookingCommand.getEndDate(),
                bookingCommand.getGuestIds()
        ));
        UUID registrationId = saveRegistrationPort.saveRegistration(registration);
        createNewRegistrationFolio(registration);
        return registrationId;
    }

    @Override
    public UUID createRegistrationFromReservation(CreateRegistrationFromReservationCommand command) {
        Registration registration = new Registration(command.getPropertyId(), command.getSource());
        RegistrationParty party = registration.addPartyFromGuestIds(command.getParty().getGuestIds());
        command.getBookings().forEach(bookingCommand -> registration.addBooking(
                bookingCommand.getAccommodationTypeId(),
                bookingCommand.getStartDate(),
                bookingCommand.getEndDate(),
                bookingCommand.getAccommodationTypeReservationId(),
                new ArrayList<>()
        ));
        UUID registrationId = saveRegistrationPort.saveRegistration(registration);
        createRegistrationFolio(registration, party, command.getFolio());
        registration.getRegistrationBookings().forEach(booking -> booking.setChargeAdded(true));
        return registrationId;
    }

    @Override
    public void confirmRegistration(ConfirmRegistrationCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationMayBeConfirmed();
        verifyAccommodationTypeAvailability(registration.getRegistrationBookings());
        reserveBooking(registration.getRegistrationBookings());
        addChargesToFolio(registration, registration.getRegistrationBookings());
        registration.confirmRegistration();
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void cancelRegistration(CancelRegistrationCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationMayBeCanceled();
        releaseBookings(registration.getRegistrationBookings());
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

    @Override
    public void addParty(AddPartyCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationIsActive();
        List<UUID> partyIds = command.getParties().stream()
                .map(party -> registration.addPartyFromGuestIds(party.getGuestIds()))
                .map(RegistrationParty::getPartyId)
                .collect(Collectors.toList());
        registrationFolioPort.addPartyFolio(registration.getRegistrationFolioId(), partyIds);
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void removeParty(RemovePartyCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationIsActive();
        registration.removeParty(command.getPartyId());
        if (!registrationFolioPort.isPartyFolioEmpty(registration.getRegistrationFolioId(), command.getPartyId())) {
            throw new RegistrationException.RegistrationWrongStatedException("party folio must be empty");
        }
        registrationFolioPort.removePartyFolio(registration.getRegistrationFolioId(), List.of(command.getPartyId()));
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void addGuestToParty(AddGuestToPartyCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationIsActive();
        command.getGuestIds().forEach(guestId -> registration.addGuestToParty(command.getPartyId(), new RegistrationGuest(guestId)));
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void removeGuestFromParty(RemoveGuestFromPartyCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationIsActive();
        command.getGuestIds().forEach(guestId -> registration.removeGuestFromParty(command.getPartyId(), new RegistrationGuest(guestId)));
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void changeGuestInParty(ChaneGuestPartyCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationIsActive();
        RegistrationGuest guest = new RegistrationGuest(command.getGuestId());
        List<RegistrationBooking> bookingsWhereChargesGoingToChange = registration.getRegistrationBookings().stream()
                .filter(booking -> booking.containsGuest(guest))
                .collect(Collectors.toList());
        registration.changeGuestParty(command.getNewPartyId(), guest);
        bookingsWhereChargesGoingToChange.forEach(booking -> this.changeChargeOnPartyFolioForBooking(registration, booking));
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void addBookingToRegistration(AddBookingToRegistrationCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationIsActive();
        List<RegistrationBooking> newBookings = command.getBookings().stream()
                .map(bookingCommand -> registration.addBooking(
                        bookingCommand.getAccommodationTypeId(),
                        bookingCommand.getStartDate(),
                        bookingCommand.getEndDate(),
                        bookingCommand.getGuestIds()
                )).collect(Collectors.toList());
        verifyAccommodationTypeAvailability(newBookings);
        if (registration.getStatus() == RegistrationStatus.CONFIRMED) {
            reserveBooking(registration.getRegistrationBookings());
            addChargesToFolio(registration, newBookings);
        }
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void removeBookingFromRegistration(RemoveBookingFromRegistrationCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationIsActive();
        List<RegistrationBooking> bookingsToRemove = registration.getBookingByIds(command.getBookingIds());
        removeChargesFromFolio(registration, bookingsToRemove);
        releaseBookings(bookingsToRemove);
        registration.removeBooking(command.getBookingIds());
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void addGuestToBooking(AddGuestToBookingCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationIsActive();
        command.getGuestIds().stream()
                .map(RegistrationGuest::new)
                .forEach(guest -> registration.addGuestToBooking(command.getBookingId(), guest));
        this.changeChargeOnPartyFolioForBooking(registration, registration.getBookingById(command.getBookingId()));
        saveRegistrationPort.saveRegistration(registration);
    }

    @Override
    public void removeGuestFromBooking(RemoveGuestFromBookingCommand command) {
        Registration registration = loadRegistrationPort.loadRegistration(command.getRegistrationId());
        registration.verifyRegistrationIsActive();
        command.getGuestIds().stream()
                .map(RegistrationGuest::new)
                .forEach(guest -> registration.removeGuestFromBooking(command.getBookingId(), guest));
        this.changeChargeOnPartyFolioForBooking(registration, registration.getBookingById(command.getBookingId()));
        saveRegistrationPort.saveRegistration(registration);
    }

    private void changeChargeOnPartyFolioForBooking(Registration registration, RegistrationBooking booking) {
        if (booking.getIsChargeAdded()) {
            registrationFolioPort.changeChargeOnPartyFolioForBooking(registration, booking);
        }
    }

    private void verifyIfRegistrationFolioIsPaid(Registration registration) {
        if (!registrationFolioPort.isRegistrationFolioPaid(registration.getRegistrationFolioId())) {
            throw new RegistrationException.RegistrationWrongStatedException("Registration folio must be paid");
        }
    }

    private void verifyAccommodationTypeAvailability(List<RegistrationBooking> bookings) {
        if (!bookings.stream().allMatch(accommodationTypeAvailabilityPort::isAccommodationTypeAvailable)) {
            throw new RegistrationException.NoAccommodationAvailableException();
        }
    }

    private void releaseBookings(List<RegistrationBooking> bookings) {
        bookings.stream()
                .filter(RegistrationBooking::isReserved)
                .peek(accommodationTypeReservationPort::releaseAccommodationTypeReservation)
                .forEach(booking -> booking.setAccommodationTypeReservationId(null));
    }

    private void reserveBooking(List<RegistrationBooking> bookings) {
        bookings.stream()
                .filter(booking -> !booking.isReserved())
                .forEach(booking -> booking.setAccommodationTypeReservationId(accommodationTypeReservationPort.reserveAccommodationType(booking)));
    }

    private void refundPayments(Registration registration) {
        registrationFolioPort.refund(registration.getRegistrationFolioId());
    }

    private void addChargesToFolio(Registration registration, List<RegistrationBooking> bookings) {
        bookings.stream()
                .filter(Predicate.not(RegistrationBooking::getIsChargeAdded))
                .peek(booking -> registrationFolioPort.addChargeToPartyFolioForBookings(registration, booking))
                .forEach(booking -> booking.setChargeAdded(true));
    }

    private void removeChargesFromFolio(Registration registration, List<RegistrationBooking> bookings) {
        bookings.stream()
                .filter(RegistrationBooking::getIsChargeAdded)
                .peek(booking -> registrationFolioPort.removeChargeFromPartyFolioForBookings(registration, booking))
                .forEach(booking -> booking.setChargeAdded(false));
    }

    private void createNewRegistrationFolio(Registration registration) {
        UUID registrationFolio = registrationFolioPort.createRegistrationFolio(registration.getRegistrationId(),
                registration.getPropertyId(), registration.getParties().stream().map(RegistrationParty::getPartyId).toList());
        registration.setRegistrationFolioId(registrationFolio);
    }

    private void createRegistrationFolio(Registration registration, RegistrationParty party, CreateRegistrationFromReservationCommand.Folio folio) {
        UUID registrationFolioId = registrationFolioPort.createRegistrationFolio(registration.getRegistrationId(), registration.getPropertyId(), party.getPartyId(), folio);
        registration.setRegistrationFolioId(registrationFolioId);
    }
}
