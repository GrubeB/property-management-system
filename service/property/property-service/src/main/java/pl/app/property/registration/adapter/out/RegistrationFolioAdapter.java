package pl.app.property.registration.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_price.application.port.in.CalculateAccommodationTypePriceCommand;
import pl.app.property.accommodation_price.application.port.in.CalculateAccommodationTypePriceUseCase;
import pl.app.property.registration.application.domain.model.Registration;
import pl.app.property.registration.application.domain.model.RegistrationBooking;
import pl.app.property.registration.application.domain.model.RegistrationParty;
import pl.app.property.registration.application.port.in.CreateRegistrationFromReservationCommand;
import pl.app.property.registration.application.port.out.RegistrationFolioPort;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioChargeType;
import pl.app.property.registration_folio.application.port.in.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
class RegistrationFolioAdapter implements RegistrationFolioPort {
    private final CreateRegistrationFolioWithExistingChargesUseCase createRegistrationFolioWithExistingChargesUseCase;
    private final CreateRegistrationFolioUseCase createRegistrationFolioUseCase;
    private final AddChargeToPartyFolioUseCase addChargeToPartyFolioUseCase;
    private final CalculateAccommodationTypePriceUseCase calculateAccommodationTypePriceUseCase;
    private final RemoveChargeFromPartyFolioUseCase removeChargeFromPartyFolioUseCase;
    private final IsRegistrationFolioPaidUseCase isRegistrationFolioPaidUseCase;

    @Override
    public UUID createRegistrationFolio(UUID partyId, CreateRegistrationFromReservationCommand.Folio folio) {
        var partyFolio = new CreateRegistrationFolioWithExistingChargesCommand.PartyFolio(
                partyId,
                folio.getPayments().stream().map(p -> new CreateRegistrationFolioWithExistingChargesCommand.Payment(
                        p.getGuestId(),
                        p.getAmount(),
                        p.getCurrent(),
                        p.getDate()
                )).collect(Collectors.toList()),
                folio.getCharges().stream().map(ch -> new CreateRegistrationFolioWithExistingChargesCommand.Charge(
                        ch.getType(),
                        ch.getName(),
                        ch.getAmount(),
                        ch.getCurrent(),
                        ch.getDate()
                )).collect(Collectors.toList())
        );
        CreateRegistrationFolioWithExistingChargesCommand command =
                new CreateRegistrationFolioWithExistingChargesCommand(List.of(partyFolio));
        return createRegistrationFolioWithExistingChargesUseCase.createRegistrationFolio(command);
    }

    @Override
    public UUID createNewRegistrationFolio(UUID registrationId, List<UUID> partyIds) {
        CreateRegistrationFolioCommand command = new CreateRegistrationFolioCommand(registrationId, partyIds);
        return createRegistrationFolioUseCase.createRegistrationFolio(command);
    }

    @Override
    public void addChargeToFolioForBooking(Registration registration) {
        registration.getRegistrationBookings()
                .stream().filter(b -> b.getChargeIds().isEmpty())
                .forEach(registrationBooking -> addChargeToPartyFolioForBookings(registrationBooking, registration));
    }

    private void addChargeToPartyFolioForBookings(RegistrationBooking registrationBooking, Registration registration) {
        BigDecimal bookingPrice = calculateAccommodationTypePriceUseCase.calculateAccommodationTypePrice(new CalculateAccommodationTypePriceCommand(registrationBooking.getAccommodationTypeId(), registrationBooking.getStart(), registrationBooking.getEnd()));
        Map<RegistrationParty, BigDecimal> priceForEachParty = registration.getPriceForEachParty(registrationBooking, bookingPrice);
        priceForEachParty.entrySet().stream()
                .map(e -> new AddChargeToPartyFolioCommand(registration.getRegistrationFolioId(), e.getKey().getPartyId(), RegistrationPartyFolioChargeType.ROOM, "ROOM", e.getValue(), "PLN"))
                .forEach(command -> {
                    UUID chargeId = addChargeToPartyFolioUseCase.addCharge(command);
                    registrationBooking.addCharge(chargeId);
                });
    }

    @Override
    public boolean isFolioPaid(UUID registrationFolioId) {
        return isRegistrationFolioPaidUseCase.isRegistrationFolioPaid(new IsRegistrationFolioPaidCommand(registrationFolioId));
    }

    @Override
    public void refund(UUID registrationFolioId) {
        // TODO
    }
}
