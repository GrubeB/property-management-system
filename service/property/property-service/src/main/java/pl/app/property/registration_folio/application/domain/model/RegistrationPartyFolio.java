package pl.app.property.registration_folio.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.property.registration_folio.application.domain.exception.RegistrationFolioException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegistrationPartyFolio {
    private UUID partyFolioId;
    private UUID partyId;
    private List<RegistrationPartyFolioPayment> payments;
    private List<RegistrationPartyFolioCharge> charges;
    private List<UUID> globalPaymentIds;

    public RegistrationPartyFolio(UUID partyId) {
        this.partyFolioId = UUID.randomUUID();
        this.partyId = partyId;
        this.payments = new ArrayList<>();
        this.charges = new ArrayList<>();
        this.globalPaymentIds = new ArrayList<>();
    }

    // CHARGE
    public RegistrationPartyFolioCharge addChargeToParty(UUID objectId, RegistrationPartyFolioChargeType type, String name, BigDecimal amount, String current) {
        RegistrationPartyFolioCharge newCharge = new RegistrationPartyFolioCharge(objectId, type, name, amount, current);
        charges.add(newCharge);
        return newCharge;
    }

    public RegistrationPartyFolioCharge addChargeToParty(UUID objectId, RegistrationPartyFolioChargeType type, String name, BigDecimal amount, String current, Instant date) {
        RegistrationPartyFolioCharge newCharge = new RegistrationPartyFolioCharge(objectId, type, name, amount, current, date);
        charges.add(newCharge);
        return newCharge;
    }

    public void removeChargeFromParty(UUID chargeId) {
        RegistrationPartyFolioCharge partyFolioCharge = charges.stream()
                .filter(ch -> ch.getChargeId().equals(chargeId))
                .findAny().orElseThrow(() -> RegistrationFolioException.NotFoundPartyFolioChargeException.fromId(chargeId));
        this.charges.remove(partyFolioCharge);
    }

    public void removeChargeFromParty(RegistrationPartyFolioCharge partyFolioCharge) {
        this.charges.remove(partyFolioCharge);
    }

    // PAYMENT
    public RegistrationPartyFolioPayment addPaymentToParty(UUID guestId, BigDecimal amount, String current) {
        RegistrationPartyFolioPayment newPayment = new RegistrationPartyFolioPayment(guestId, amount, current);
        payments.add(newPayment);
        return newPayment;
    }

    public RegistrationPartyFolioPayment addPaymentToParty(UUID guestId, BigDecimal amount, String current, Instant date) {
        RegistrationPartyFolioPayment newPayment = new RegistrationPartyFolioPayment(guestId, amount, current, date);
        payments.add(newPayment);
        return newPayment;
    }

    public Boolean isFolioPaid() {
        return getBalanceOfFolio().compareTo(BigDecimal.ZERO) >= 0;
    }

    public BigDecimal getBalanceOfFolio() {
        BigDecimal paymentAmount = payments.stream()
                .map(RegistrationPartyFolioPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal chargeAmount = charges.stream()
                .map(RegistrationPartyFolioCharge::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return paymentAmount.subtract(chargeAmount);
    }


    public boolean isEmpty() {
        return this.charges.isEmpty() && this.payments.isEmpty();
    }

    public void addGlobalPaymentId(UUID paymentId) {
        this.globalPaymentIds.add(paymentId);
    }
}
