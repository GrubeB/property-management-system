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
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioCharge;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioChargeType;
import pl.app.property.registration_folio.application.port.in.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final isPartyFolioEmptyUseCase isPartyFolioEmptyUseCase;
    private final FetchPaymentsByObjectIdUseCase fetchPaymentsByObjectIdUseCase;
    private final AddPartyFolioUseCase addPartyFolioUseCase;
    private final RemovePartyFolioUseCase removePartyFolioUseCase;
    private final RefundRegistrationFolioUseCase refundRegistrationFolioUseCase;

    @Override
    public UUID createRegistrationFolio(UUID registrationId, UUID propertyId, UUID partyId, CreateRegistrationFromReservationCommand.Folio folio) {
        var partyFolio = new CreateRegistrationFolioWithExistingChargesCommand.PartyFolio(partyId,
                mapPayments(folio.getPayments()), mapCharges(folio.getCharges()));
        CreateRegistrationFolioWithExistingChargesCommand command =
                new CreateRegistrationFolioWithExistingChargesCommand(registrationId, propertyId, List.of(partyFolio));
        return createRegistrationFolioWithExistingChargesUseCase.createRegistrationFolio(command);
    }

    private List<CreateRegistrationFolioWithExistingChargesCommand.Charge> mapCharges(List<CreateRegistrationFromReservationCommand.Charge> charges) {
        List<CreateRegistrationFromReservationCommand.Charge> chargesWithoutObjectId = charges.stream()
                .filter(charge -> charge.getObjectId() == null)
                .toList();
        List<CreateRegistrationFromReservationCommand.Charge> mergedChargesWithTheSameObjectId = charges.stream()
                .filter(charge -> charge.getObjectId() != null)
                .collect(Collectors.groupingBy(CreateRegistrationFromReservationCommand.Charge::getObjectId))
                .values().stream()
                .map(list -> list.stream()
                        .reduce(new CreateRegistrationFromReservationCommand.Charge(), (target, next) -> {
                            target.setChargeId(next.getChargeId());
                            target.setObjectId(next.getObjectId());
                            target.setType(next.getType());
                            target.setName(next.getName());
                            if (target.getAmount() == null) {
                                target.setAmount(next.getAmount());
                            } else {
                                target.setAmount(target.getAmount().add(next.getAmount()));
                            }
                            target.setCurrency(next.getCurrency());
                            target.setDate(next.getDate());
                            return target;
                        }))
                .toList();

        return Stream.concat(chargesWithoutObjectId.stream(), mergedChargesWithTheSameObjectId.stream())
                .map(charge -> new CreateRegistrationFolioWithExistingChargesCommand.Charge(
                        charge.getObjectId(),
                        charge.getType(),
                        charge.getName(),
                        charge.getAmount(),
                        charge.getCurrency(),
                        charge.getDate()
                )).collect(Collectors.toList());
    }

    private List<CreateRegistrationFolioWithExistingChargesCommand.Payment> mapPayments(List<CreateRegistrationFromReservationCommand.Payment> payments) {
        return payments.stream().map(payment -> new CreateRegistrationFolioWithExistingChargesCommand.Payment(
                payment.getGuestId(),
                payment.getAmount(),
                payment.getCurrent(),
                payment.getDate()
        )).collect(Collectors.toList());
    }


    @Override
    public UUID createRegistrationFolio(UUID registrationId, UUID propertyId, List<UUID> partyIds) {
        CreateRegistrationFolioCommand command = new CreateRegistrationFolioCommand(registrationId, propertyId, partyIds);
        return createRegistrationFolioUseCase.createRegistrationFolio(command);
    }

    @Override
    public void addChargeToPartyFolioForBookings(Registration registration, RegistrationBooking booking) {
        BigDecimal bookingPrice = calculateAccommodationTypePriceUseCase.calculateAccommodationTypePrice(new CalculateAccommodationTypePriceCommand(booking.getAccommodationTypeId(), booking.getStart(), booking.getEnd()));
        Map<RegistrationParty, BigDecimal> priceForEachParty = registration.getPriceForEachParty(booking, bookingPrice);
        priceForEachParty.entrySet().stream()
                .map(e -> new AddChargeToPartyFolioCommand(registration.getRegistrationFolioId(), e.getKey().getPartyId(),
                        booking.getAccommodationTypeReservationId(), RegistrationPartyFolioChargeType.ROOM, "ROOM",
                        e.getValue(), "PLN"))
                .forEach(addChargeToPartyFolioUseCase::addCharge);
    }

    @Override
    public void removeChargeFromPartyFolioForBookings(Registration registration, RegistrationBooking booking) {
        List<RegistrationPartyFolioCharge> registrationPartyFolioCharges = fetchPaymentsByObjectIdUseCase.fetchChargesByObjectId(new FetchChargesByObjectIdCommand(
                registration.getRegistrationFolioId(), booking.getAccommodationTypeReservationId(),
                RegistrationPartyFolioChargeType.ROOM
        ));
        registrationPartyFolioCharges.forEach(charge -> removeChargeFromPartyFolioUseCase.removeCharge(
                new RemoveChargeFromPartyFolioCommand(registration.getRegistrationFolioId(), charge.getChargeId())));
    }

    @Override
    public void changeChargeOnPartyFolioForBooking(Registration registration, RegistrationBooking booking) {
        List<RegistrationPartyFolioCharge> registrationPartyFolioCharges = fetchPaymentsByObjectIdUseCase.fetchChargesByObjectId(new FetchChargesByObjectIdCommand(
                registration.getRegistrationFolioId(), booking.getAccommodationTypeReservationId(),
                RegistrationPartyFolioChargeType.ROOM
        ));
        BigDecimal bookingPrice = registrationPartyFolioCharges.stream()
                .map(RegistrationPartyFolioCharge::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        registrationPartyFolioCharges.forEach(charge -> removeChargeFromPartyFolioUseCase.removeCharge(
                new RemoveChargeFromPartyFolioCommand(registration.getRegistrationFolioId(), charge.getChargeId())));

        Map<RegistrationParty, BigDecimal> priceForEachParty = registration.getPriceForEachParty(booking, bookingPrice);
        priceForEachParty.entrySet().stream()
                .map(e -> new AddChargeToPartyFolioCommand(registration.getRegistrationFolioId(), e.getKey().getPartyId(),
                        booking.getAccommodationTypeReservationId(), RegistrationPartyFolioChargeType.ROOM, "ROOM",
                        e.getValue(), "PLN"))
                .forEach(addChargeToPartyFolioUseCase::addCharge);
    }

    @Override
    public void refund(UUID registrationFolioId) {
        refundRegistrationFolioUseCase.refundRegistrationFolio(new RefundRegistrationFolioCommand(registrationFolioId));
    }

    @Override
    public boolean isRegistrationFolioPaid(UUID registrationFolioId) {
        return isRegistrationFolioPaidUseCase.isRegistrationFolioPaid(new IsRegistrationFolioPaidCommand(registrationFolioId));
    }

    @Override
    public boolean isPartyFolioEmpty(UUID registrationFolioId, UUID partyId) {
        return isPartyFolioEmptyUseCase.isPartyFolioEmpty(new IsPartyFolioEmptyCommand(registrationFolioId, partyId));
    }

    @Override
    public void addPartyFolio(UUID registrationFolioId, List<UUID> partyIds) {
        addPartyFolioUseCase.addPartyFolio(new AddPartyFolioCommand(registrationFolioId, partyIds));
    }

    @Override
    public void removePartyFolio(UUID registrationFolioId, List<UUID> partyIds) {
        removePartyFolioUseCase.removePartyFolio(new RemovePartyFolioCommand(registrationFolioId, partyIds));
    }
}
