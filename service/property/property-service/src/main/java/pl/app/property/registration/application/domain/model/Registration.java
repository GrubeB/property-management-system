package pl.app.property.registration.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.property.registration.application.domain.exception.RegistrationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static java.math.RoundingMode.HALF_UP;

@Getter
@AllArgsConstructor
public class Registration {
    private UUID registrationId;
    private UUID propertyId;
    private RegistrationStatus status;
    private RegistrationSource source;
    private List<RegistrationParty> parties;
    private UUID registrationFolioId;

    private List<RegistrationBooking> registrationBookings;

    public Registration(UUID propertyId, RegistrationSource source) {
        this.registrationId = UUID.randomUUID();
        this.propertyId = propertyId;
        this.status = RegistrationStatus.PENDING;
        this.source = source;
        this.parties = new ArrayList<>();
        this.registrationFolioId = null;
        this.registrationBookings = new ArrayList<>();
    }

    public void setRegistrationFolioId(UUID registrationFolioId) {
        this.registrationFolioId = registrationFolioId;
    }

    public Map<RegistrationParty, BigDecimal> getPriceForEachParty(RegistrationBooking registrationBooking, BigDecimal bookingPrice) {
        List<RegistrationGuest> bookingGuests = registrationBooking.getGuests();
        if (bookingGuests.isEmpty()) {
            throw new RegistrationException.BookingWrongStatedException("Booking must have guest");
        }
        // all belong to one party
        Optional<RegistrationParty> partyWhereAllGuestsBelong = parties.stream()
                .filter(registrationParty -> registrationParty.isAllGuestInParty(bookingGuests))
                .findAny();
        if (partyWhereAllGuestsBelong.isPresent()) {
            return Collections.singletonMap(partyWhereAllGuestsBelong.get(), bookingPrice);
        }
        // shared price
        BigDecimal pricePerGuest = bookingPrice.divide(BigDecimal.valueOf(bookingGuests.size()), 2, HALF_UP);
        Map<RegistrationParty, BigDecimal> priceForParty = new HashMap<>(2);
        bookingGuests.forEach(bookingGuest -> {
            RegistrationParty registrationPartyWhereGuestBelong = this.parties.stream().filter(registrationParty -> registrationParty.isGuestInParty(bookingGuest)).findAny()
                    .orElseThrow(() -> new RegistrationException.BookingWrongStatedException("Booking guest must belong to party"));
            BigDecimal currentPrice = priceForParty.computeIfAbsent(registrationPartyWhereGuestBelong, k -> BigDecimal.ZERO);
            priceForParty.put(registrationPartyWhereGuestBelong, currentPrice.add(pricePerGuest));
        });
        return priceForParty;
    }

    // PARTY
    public RegistrationParty createParty(List<RegistrationGuest> guests) {
        RegistrationParty registrationParty = new RegistrationParty(guests);
        this.parties.add(registrationParty);
        return registrationParty;
    }

    public void addGuestToParty(UUID partyId, RegistrationGuest guest) {
        RegistrationParty registrationParty = getParty(partyId);
        registrationParty.addGuest(guest);
    }

    public void removeGuestToParty(UUID partyId, RegistrationGuest guest) {
        RegistrationParty registrationParty = getParty(partyId);
        registrationParty.removeGuest(guest);
    }

    // BOOKING
    public RegistrationBooking addBooking(UUID accommodationTypeId, LocalDate startDate, LocalDate endDate, List<RegistrationGuest> guests) {
        RegistrationBooking registrationBooking = new RegistrationBooking(startDate, endDate, accommodationTypeId, guests);
        this.registrationBookings.add(registrationBooking);
        return registrationBooking;
    }

    public RegistrationBooking addBooking(UUID accommodationTypeId, LocalDate startDate, LocalDate endDate, UUID accommodationTypeReservationId, List<UUID> chargeIds, List<RegistrationGuest> guests) {
        RegistrationBooking registrationBooking = new RegistrationBooking(startDate, endDate, accommodationTypeId, accommodationTypeReservationId, chargeIds, guests);
        this.registrationBookings.add(registrationBooking);
        return registrationBooking;
    }

    public void removeBooking(UUID bookingId) {
        RegistrationBooking registrationBooking = getBooking(bookingId);
        this.registrationBookings.remove(registrationBooking);
    }

    public void addGuestToBooking(UUID bookingId, RegistrationGuest guest) {
        RegistrationBooking registrationBooking = getBooking(bookingId);
        registrationBooking.addGuest(guest);
    }

    public void removeGuestToBooking(UUID bookingId, RegistrationGuest guest) {
        RegistrationBooking registrationBooking = getBooking(bookingId);
        registrationBooking.removeGuest(guest);
    }

    // STATUS
    public void verifyRegistrationMayBeConfirmed() {
        if (!this.status.equals(RegistrationStatus.PENDING)) {
            throw new RegistrationException.NotFoundRegistrationException("Only reservation in PENDING state can be confirmed");
        }
    }

    public void confirmRegistration() {
        this.verifyRegistrationMayBeConfirmed();
        this.status = RegistrationStatus.CONFIRMED;
    }

    public void verifyRegistrationMayBeCanceled() {
        if (!this.status.equals(RegistrationStatus.CONFIRMED)) {
            throw new RegistrationException.NotFoundRegistrationException("Only reservation in CONFIRMED state can be canceled");
        }
    }

    public void cancelRegistration() {
        this.verifyRegistrationMayBeCanceled();
        this.status = RegistrationStatus.CANCELED;
    }

    public void verifyRegistrationMayBeFinished() {
        if (!this.status.equals(RegistrationStatus.CONFIRMED)) {
            throw new RegistrationException.NotFoundRegistrationException("Only reservation in CONFIRMED state can be finished");
        }
    }

    public void finishRegistration() {
        this.verifyRegistrationMayBeFinished();
        this.status = RegistrationStatus.FINISHED;
    }

    // GETTERS
    private RegistrationParty getParty(UUID partyId) {
        return parties.stream()
                .filter(pf -> pf.getPartyId().equals(partyId))
                .findAny()
                .orElseThrow(() -> RegistrationException.NotFoundPartyException.fromId(partyId));
    }

    private RegistrationBooking getBooking(UUID bookingId) {
        return registrationBookings.stream()
                .filter(pf -> pf.getBookingId().equals(bookingId))
                .findAny()
                .orElseThrow(() -> RegistrationException.NotFoundAccommodationTypeBookingException.fromId(bookingId));
    }
}
