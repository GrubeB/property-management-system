package pl.app.property.reservation.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.accommodation_price.application.port.in.CalculateAccommodationTypePriceCommand;
import pl.app.property.accommodation_price.application.port.in.CalculateAccommodationTypePriceUseCase;
import pl.app.property.reservation.application.domain.model.Reservation;
import pl.app.property.reservation.application.port.out.ReservationFolioPort;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolioChargeType;
import pl.app.property.reservation_folio.application.port.in.*;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;
import pl.app.property.reservation_payment_policy.application.port.in.FetchReservationPaymentPolicyCommand;
import pl.app.property.reservation_payment_policy.application.port.in.FetchReservationPaymentPolicyUseCase;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class ReservationFolioAdapter implements
        ReservationFolioPort {

    private final FetchReservationPaymentPolicyUseCase fetchReservationPaymentPolicyUseCase;
    private final CreateReservationFolioUseCase createReservationFolioUseCase;
    private final CalculateAccommodationTypePriceUseCase calculateAccommodationTypePriceUseCase;
    private final AddChargeFolioUseCase addChargeFolioUseCase;
    private final IsReservationFolioPaidUseCase isReservationFolioPaidUseCase;

    @Override
    public UUID createFolio(UUID reservationId, UUID propertyId) {
        ReservationPaymentPolicy fetch = fetchReservationPaymentPolicyUseCase.fetch(new FetchReservationPaymentPolicyCommand(propertyId));
        return createReservationFolioUseCase.createReservationFolio(new CreateReservationFolioCommand(reservationId, fetch.getType()));
    }

    @Override
    public void addChargeToFolioForBooking(Reservation reservation) {
        ReservationPaymentPolicy reservationPaymentPolicy = fetchReservationPaymentPolicyUseCase.fetch(new FetchReservationPaymentPolicyCommand(reservation.getPropertyId()));
        switch (reservationPaymentPolicy.getType()) {
            case FULL -> reservation.getReservationBookings().forEach(rb -> {
                BigDecimal fullBookingPrice = calculateAccommodationTypePriceUseCase.calculateAccommodationTypePrice(
                        new CalculateAccommodationTypePriceCommand(rb.getAccommodationTypeId(), rb.getStart(), rb.getEnd())
                );
                AddChargeCommand addChargeCommand = new AddChargeCommand(reservation.getReservationFolioId(),
                        rb.getReservationBookingId(), ReservationFolioChargeType.ROOM, "ROOM", fullBookingPrice, "PLN", true);
                UUID chargeId = addChargeFolioUseCase.addCharge(addChargeCommand);
            });
            case FIRST_DAY -> reservation.getReservationBookings().forEach(rb -> {
                BigDecimal fullBookingPrice = calculateAccommodationTypePriceUseCase.calculateAccommodationTypePrice(
                        new CalculateAccommodationTypePriceCommand(rb.getAccommodationTypeId(), rb.getStart(), rb.getEnd())
                );
                BigDecimal bookingPriceForFirstDay = calculateAccommodationTypePriceUseCase.calculateAccommodationTypePrice(
                        new CalculateAccommodationTypePriceCommand(rb.getAccommodationTypeId(), rb.getStart(), rb.getStart().plusDays(1))
                );
                AddChargeCommand addChargeCommandThatShouldBePaid = new AddChargeCommand(reservation.getReservationFolioId(),
                        rb.getReservationBookingId(), ReservationFolioChargeType.ROOM, "ROOM",
                        bookingPriceForFirstDay, "PLN", true);
                UUID chargeThatShouldBePaidId = addChargeFolioUseCase.addCharge(addChargeCommandThatShouldBePaid);
                if (fullBookingPrice.subtract(bookingPriceForFirstDay).compareTo(BigDecimal.ZERO) == 0) {
                    return;
                }
                AddChargeCommand addChargeCommand = new AddChargeCommand(reservation.getReservationFolioId(),
                        rb.getReservationBookingId(), ReservationFolioChargeType.ROOM, "ROOM",
                        fullBookingPrice.subtract(bookingPriceForFirstDay), "PLN", false);
                UUID chargeId = addChargeFolioUseCase.addCharge(addChargeCommand);
            });
            case FIXED -> throw new RuntimeException("FIXED type is not supported yet");
            case NONE -> reservation.getReservationBookings().forEach(rb -> {
                BigDecimal fullBookingPrice = calculateAccommodationTypePriceUseCase.calculateAccommodationTypePrice(
                        new CalculateAccommodationTypePriceCommand(rb.getAccommodationTypeId(), rb.getStart(), rb.getEnd())
                );
                AddChargeCommand addChargeCommand = new AddChargeCommand(reservation.getReservationFolioId(),
                        rb.getReservationBookingId(), ReservationFolioChargeType.ROOM, "ROOM",
                        fullBookingPrice, "PLN", false);
                UUID chargeId = addChargeFolioUseCase.addCharge(addChargeCommand);
            });
        }
    }

    @Override
    public boolean isFolioPaid(UUID reservationFolioId) {
        return isReservationFolioPaidUseCase.isReservationFolioPaid(new IsReservationFolioPaidCommand(reservationFolioId));
    }

    @Override
    public void refund(UUID reservationFolioId) {
        // TODO return money to guest
    }
}
