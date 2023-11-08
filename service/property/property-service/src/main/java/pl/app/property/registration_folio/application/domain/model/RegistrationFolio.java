package pl.app.property.registration_folio.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.property.registration.application.domain.exception.RegistrationException;
import pl.app.property.registration_folio.application.domain.exception.RegistrationFolioException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RegistrationFolio {
    private UUID registrationFolioId;
    private UUID registrationId;
    private UUID propertyId;
    private List<RegistrationPartyFolio> partyFolios;

    public RegistrationFolio(UUID registrationId, UUID propertyId) {
        this.registrationFolioId = UUID.randomUUID();
        this.registrationId = registrationId;
        this.propertyId = propertyId;
        this.partyFolios = new ArrayList<>();
    }

    public RegistrationFolio(UUID registrationId, UUID propertyId, List<UUID> partyIds) {
        this.registrationFolioId = UUID.randomUUID();
        this.registrationId = registrationId;
        this.propertyId = propertyId;
        this.partyFolios = new ArrayList<>();
        partyIds.stream()
                .map(RegistrationPartyFolio::new)
                .forEach(this::addParty);
    }

    public void addParty(RegistrationPartyFolio partyFolio) {
        if (this.partyFolios.stream().anyMatch(p -> p.getPartyId().equals(partyFolio.getPartyId()))) {
            throw new RegistrationException.RegistrationFolioWrongStatedException("there is already folio for party with id: " + partyFolio.getPartyId());
        }
        this.partyFolios.add(partyFolio);
    }

    public void removePartyByPartyId(UUID partyId) {
        if (!this.isPartyFolioEmpty(partyId)) {
            throw new RegistrationException.RegistrationFolioWrongStatedException("party folio is not empty");
        }
        this.partyFolios.removeIf(partyFolio -> Objects.equals(partyFolio.getPartyId(), partyId));
    }

    public Boolean isRegistrationFolioPaid() {
        return getBalanceOfFolio().compareTo(BigDecimal.ZERO) >= 0;
    }

    public Boolean isPartyFolioPaid(UUID partyId) {
        RegistrationPartyFolio partyFolio = getPartyFolioByPartyId(partyId);
        return partyFolio.isFolioPaid();
    }

    public boolean isPartyFolioEmpty(UUID partyId) {
        RegistrationPartyFolio partyFolio = getPartyFolioByPartyId(partyId);
        return partyFolio.isEmpty();
    }

    public BigDecimal getBalanceOfFolio() {
        return partyFolios.stream()
                .map(RegistrationPartyFolio::getBalanceOfFolio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public RegistrationPartyFolioCharge addChargeToPartyFolio(UUID partyId, UUID objectId, RegistrationPartyFolioChargeType type, String name, BigDecimal amount, String current) {
        RegistrationPartyFolio partyFolio = getPartyFolioByPartyId(partyId);
        return partyFolio.addChargeToParty(objectId, type, name, amount, current);
    }

    public void removeChargeFromPartyFolio(UUID partyId, UUID chargeId) {
        RegistrationPartyFolio partyFolio = getPartyFolioByPartyId(partyId);
        partyFolio.removeChargeFromParty(chargeId);
    }

    public void removeChargeFromPartyFolio(UUID chargeId) {
        RegistrationPartyFolioCharge partyFolioCharge = this.partyFolios.stream()
                .map(RegistrationPartyFolio::getCharges)
                .flatMap(List::stream)
                .filter(ch -> ch.getChargeId().equals(chargeId))
                .findAny().orElseThrow(() -> RegistrationFolioException.NotFoundPartyFolioChargeException.fromId(chargeId));
        this.partyFolios.forEach(p -> p.removeChargeFromParty(partyFolioCharge));
    }

    public RegistrationPartyFolioPayment addNewPaymentToPartyFolio(UUID partyFolioId, UUID guestId, BigDecimal amount, String current) {
        RegistrationPartyFolio partyFolio = getPartyFolioByPartyFolioId(partyFolioId);
        return partyFolio.addPaymentToParty(guestId, amount, current);
    }

    private RegistrationPartyFolio getPartyFolioByPartyId(UUID partyId) {
        return this.partyFolios.stream()
                .filter(pf -> pf.getPartyId().equals(partyId))
                .findAny().orElseThrow(() -> RegistrationFolioException.NotFoundPartyFolioException.fromPartyId(partyId));
    }

    private RegistrationPartyFolio getPartyFolioByPartyFolioId(UUID partyFolioId) {
        return this.partyFolios.stream()
                .filter(pf -> pf.getPartyFolioId().equals(partyFolioId))
                .findAny().orElseThrow(() -> RegistrationFolioException.NotFoundPartyFolioException.fromId(partyFolioId));
    }

    public List<RegistrationPartyFolioCharge> getChargesByObjectIdAndType(UUID objectId, RegistrationPartyFolioChargeType type) {
        return this.partyFolios.stream()
                .map(RegistrationPartyFolio::getCharges)
                .flatMap(List::stream)
                .filter(charge -> Objects.equals(charge.getObjectId(), objectId))
                .filter(charge -> Objects.equals(charge.getType(), type))
                .collect(Collectors.toList());
    }
}
