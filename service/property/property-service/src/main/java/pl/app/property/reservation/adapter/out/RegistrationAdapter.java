package pl.app.property.reservation.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.registration.application.domain.model.RegistrationSource;
import pl.app.property.registration.application.port.in.CreateRegistrationFromReservationCommand;
import pl.app.property.registration.application.port.in.CreateRegistrationFromReservationUseCase;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioChargeType;
import pl.app.property.reservation.application.domain.model.Reservation;
import pl.app.property.reservation.application.domain.model.ReservationGuest;
import pl.app.property.reservation.application.domain.model.ReservationSource;
import pl.app.property.reservation.application.port.out.RegistrationFolioPort;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolio;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolioChargeType;
import pl.app.property.reservation_folio.application.port.in.FetchReservationFolioCommand;
import pl.app.property.reservation_folio.application.port.in.FetchReservationFolioUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
class RegistrationAdapter implements RegistrationFolioPort {

    private final CreateRegistrationFromReservationUseCase createRegistrationFromReservationUseCase;
    private final FetchReservationFolioUseCase fetchReservationFolioUseCase;

    @Override
    public UUID createRegistration(Reservation reservation) {
        var party = getParty(reservation);
        var bookings = getBookings(reservation);
        var folio = getFolio(reservation);
        var command = new CreateRegistrationFromReservationCommand(reservation.getPropertyId(),
                map(reservation.getSource()), party, bookings, folio);
        return createRegistrationFromReservationUseCase.createRegistration(command);
    }

    private CreateRegistrationFromReservationCommand.Folio getFolio(Reservation reservation) {
        ReservationFolio reservationFolio = fetchReservationFolioUseCase.fetch(new FetchReservationFolioCommand(reservation.getReservationFolioId()));
        var charges = reservationFolio.getCharges().stream().map(ch -> new CreateRegistrationFromReservationCommand.Charge(
                map(ch.getType()),
                ch.getName(),
                ch.getAmount(),
                ch.getCurrent(),
                ch.getDate()
        )).toList();
        var payments = reservationFolio.getPayments().stream().map(p -> new CreateRegistrationFromReservationCommand.Payment(
                p.getGuestId(),
                p.getAmount(),
                p.getCurrent(),
                p.getDate()
        )).toList();
        return new CreateRegistrationFromReservationCommand.Folio(payments, charges);
    }

    private List<CreateRegistrationFromReservationCommand.Booking> getBookings(Reservation reservation) {
        return reservation.getReservationBookings().stream()
                .map(rb -> new CreateRegistrationFromReservationCommand.Booking(
                        rb.getStart(),
                        rb.getEnd(),
                        rb.getAccommodationTypeId(),
                        rb.getAccommodationTypeReservationId(),
                        rb.getChargeIds()
                )).toList();
    }

    private CreateRegistrationFromReservationCommand.Party getParty(Reservation reservation) {
        return new CreateRegistrationFromReservationCommand.Party(this.getGuestIds(reservation));
    }

    private List<UUID> getGuestIds(Reservation reservation) {
        List<UUID> guestIds = new ArrayList<>(reservation.getOtherGuests().stream()
                .map(ReservationGuest::getGuestId).toList());
        guestIds.add(reservation.getMainGuest().getGuestId());
        var party = new CreateRegistrationFromReservationCommand.Party(guestIds);
        return guestIds;
    }

    private RegistrationSource map(ReservationSource source) {
        return switch (source) {
            case WEBSITE -> RegistrationSource.WEBSITE;
            case WALK_IN -> RegistrationSource.WALK_IN;
            case PHONE -> RegistrationSource.PHONE;
            case EMAIL -> RegistrationSource.EMAIL;
            case OTAS -> RegistrationSource.OTAS;
            case OTHER -> RegistrationSource.OTHER;
        };
    }

    private RegistrationPartyFolioChargeType map(ReservationFolioChargeType type) {
        return switch (type) {
            case ROOM -> RegistrationPartyFolioChargeType.ROOM;
            case FOOD -> RegistrationPartyFolioChargeType.FOOD;
            case TELEPHONE -> RegistrationPartyFolioChargeType.TELEPHONE;
            case CLEANING -> RegistrationPartyFolioChargeType.CLEANING;
            case OTHER -> RegistrationPartyFolioChargeType.OTHER;
        };
    }
}
