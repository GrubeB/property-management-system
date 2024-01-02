package pl.app.property.reservation_folio.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.property.reservation_folio.application.domain.exception.ReservationFolioException;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicyType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReservationFolio {
    private UUID reservationFolioId;
    private UUID reservationId;
    private UUID propertyId;
    private ReservationPaymentPolicyType type;
    private List<ReservationFolioPayment> payments;
    private List<ReservationFolioCharge> charges;
    private List<UUID> globalPaymentIds;

    public ReservationFolio(UUID reservationId, ReservationPaymentPolicyType type) {
        this.reservationFolioId = UUID.randomUUID();
        this.reservationId = reservationId;
        this.type = type;
        this.payments = new ArrayList<>();
        this.charges = new ArrayList<>();
        this.globalPaymentIds = new ArrayList<>();
    }

    public ReservationFolioCharge addCharge(UUID objectId, ReservationFolioChargeType type, String name, BigDecimal amount, String current, Boolean shouldByPaidBeforeRegistration) {
        ReservationFolioCharge newCharge = new ReservationFolioCharge(objectId, type, name, amount, current, shouldByPaidBeforeRegistration);
        charges.add(newCharge);
        return newCharge;
    }

    public ReservationFolioPayment addPayment(UUID guestId, BigDecimal amount, String current) {
        ReservationFolioPayment newPayment = new ReservationFolioPayment(guestId, amount, current);
        payments.add(newPayment);
        return newPayment;
    }

    public Boolean isFolioPaid() {
        return getBalanceOfFolio().compareTo(BigDecimal.ZERO) >= 0;
    }

    public BigDecimal getBalanceOfFolio() {
        BigDecimal paymentAmount = payments.stream()
                .map(ReservationFolioPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal chargeAmount = charges.stream()
                .filter(ReservationFolioCharge::getShouldByPaidBeforeRegistration) // only charges that should be paid
                .map(ReservationFolioCharge::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return paymentAmount.subtract(chargeAmount);
    }

    public void removeCharge(UUID chargeId) {
        ReservationFolioCharge reservationFolioCharge = charges.stream()
                .filter(ch -> ch.getChargeId().equals(chargeId))
                .findAny().orElseThrow(() -> ReservationFolioException.NotFoundReservationFolioChargeException.fromId(chargeId));
        this.charges.remove(reservationFolioCharge);
    }

    public void addGlobalPaymentId(UUID paymentId) {
        this.globalPaymentIds.add(paymentId);
    }
}
