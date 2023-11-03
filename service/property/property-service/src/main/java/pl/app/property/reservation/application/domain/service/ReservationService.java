package pl.app.property.reservation.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.reservation.application.domain.exception.ReservationException;
import pl.app.property.reservation.application.domain.model.Reservation;
import pl.app.property.reservation.application.domain.model.ReservationGuest;
import pl.app.property.reservation.application.port.in.*;
import pl.app.property.reservation.application.port.out.*;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicyType;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
class ReservationService implements
        RejectNoPaidReservationsUseCase,
        RejectNoConfirmedReservationsUseCase,
        FinishReservationUseCase,
        CancelReservationUseCase,
        ConfirmReservationUseCase,
        CreateReservationUseCase,
        RejectReservationUseCase {
    private final LoadReservationsPort loadReservationsPort;
    private final SaveReservationsPort saveReservationsPort;
    private final ReservationFolioPort reservationFolioPort;
    private final ReservationPaymentPolicyPort reservationPaymentPolicyPort;

    private final AccommodationTypeAvailabilityPort accommodationTypeAvailabilityPort;
    private final AccommodationTypeReservationPort accommodationTypeReservationPort;
    private final RegistrationFolioPort registrationFolioPort;


    @Override
    public UUID createReservation(CreateReservationCommand command) {
        final ReservationGuest mainGuest = new ReservationGuest(command.getMainGuestId());
        final List<ReservationGuest> anotherGuests = command.getAnotherGuestIds().stream().map(ReservationGuest::new).collect(Collectors.toList());
        Reservation reservation = new Reservation(command.getPropertyId(), command.getReservationSource(), mainGuest, anotherGuests);
        addBookingsToReservation(reservation, command.getBookings());
        verifyAccommodationTypeAvailability(reservation);
        UUID reservationId = saveReservationsPort.saveReservation(reservation);
        createReservationFolio(reservation);
        return reservationId;
    }

    @Override
    public void confirmedReservation(ConfirmReservationCommand command) {
        Reservation reservation = loadReservationsPort.loadReservation(command.getReservationId());
        reservation.verifyReservationMayBeConfirmed();
        verifyAccommodationTypeAvailability(reservation);
        reserveBookings(reservation);
        addChargesToFolio(reservation);
        reservation.confirmReservation();
        saveReservationsPort.saveReservation(reservation);
    }

    @Override
    public void cancelReservation(CancelReservationCommand command) {
        Reservation reservation = loadReservationsPort.loadReservation(command.getReservationId());
        reservation.verifyReservationMayBeCanceled();
        releaseAccommodationTypeReservations(reservation);
        refundPayments(reservation);
        reservation.cancelReservation();
        saveReservationsPort.saveReservation(reservation);
    }

    @Override
    public UUID finishReservation(FinishReservationCommand command) {
        Reservation reservation = loadReservationsPort.loadReservation(command.getReservationId());
        reservation.verifyReservationMayBeFinished();
        verifyReservationFolioIsPaid(reservation.getReservationFolioId());
        UUID registrationId = createRegistration(reservation);
        reservation.finishReservation();
        saveReservationsPort.saveReservation(reservation);
        return registrationId;
    }

    @Override
    public void rejectReservation(RejectReservationCommand command) {
        Reservation reservation = loadReservationsPort.loadReservation(command.getReservationId());
        rejectReservation(reservation);
    }

    @Override
    public void rejectNoConfirmedReservations(RejectNoConfirmedReservationsCommand command) {
        List<UUID> reservationIdsToReject = loadReservationsPort.loadPendingReservationIdsCreatedDayBefore(command.getPropertyId());
        reservationIdsToReject.forEach(reservationId -> this.rejectReservation(new RejectReservationCommand(reservationId)));
    }

    @Override
    public void rejectNoPaidReservations(RejectNoPaidReservationsCommand command) {
        ReservationPaymentPolicy policy = reservationPaymentPolicyPort.fetchReservationPaymentPolicy(command.getPropertyId());
        if (policy.getType() != ReservationPaymentPolicyType.NONE) {
            List<UUID> confirmedReservationIds = loadReservationsPort.loadConfirmedReservationIds(command.getPropertyId());
            confirmedReservationIds.forEach(reservationId -> {
                Reservation reservation = loadReservationsPort.loadReservation(reservationId);
                if (reservation.shouldReservationByPaid(policy.getNumberOfDaysBeforeRegistration())) {
                    if (!reservationFolioPort.isFolioPaid(reservation.getReservationFolioId())) {
                        this.rejectReservation(reservation);
                    }
                }
            });
        }
    }

    private void rejectReservation(Reservation reservation) {
        reservation.verifyReservationMayBeRejected();
        releaseAccommodationTypeReservations(reservation);
        refundPayments(reservation);
        reservation.rejectReservation();
        saveReservationsPort.saveReservation(reservation);
    }

    private void refundPayments(Reservation reservation) {
        reservationFolioPort.refund(reservation.getReservationFolioId());
    }

    private UUID createRegistration(Reservation reservation) {
        return registrationFolioPort.createRegistration(reservation);
    }

    private void verifyAccommodationTypeAvailability(Reservation reservation) {
        if (!accommodationTypeAvailabilityPort.isAccommodationTypeAvailable(reservation)) {
            throw new ReservationException.NoAccommodationAvailableException();
        }
    }

    private void releaseAccommodationTypeReservations(Reservation reservation) {
        reservation.getReservationBookings()
                .stream().filter(r -> r.getAccommodationTypeReservationId() != null)
                .forEach(accommodationTypeReservationPort::releaseAccommodationTypeReservation);
    }

    private void createReservationFolio(Reservation reservation) {
        UUID reservationFolioId = reservationFolioPort.createFolio(reservation.getReservationId(), reservation.getPropertyId());
        reservation.setReservationFolioId(reservationFolioId);
    }

    private void addChargesToFolio(Reservation reservation) {
        reservationFolioPort.addChargeToFolioForBooking(reservation);
    }

    private void reserveBookings(Reservation reservation) {
        reservation.getReservationBookings().forEach(booking -> {
            UUID accommodationTypeReservationId = accommodationTypeReservationPort.reserveAccommodationType(booking);
            booking.setAccommodationTypeReservationId(accommodationTypeReservationId);
        });
    }

    private void verifyReservationFolioIsPaid(UUID reservationFolioId) {
        if (!reservationFolioPort.isFolioPaid(reservationFolioId)) {
            throw new ReservationException.ReservationFolioIsNotPaidException();
        }
    }

    private void addBookingsToReservation(Reservation reservation, List<CreateReservationCommand.Booking> commandBookings) {
        commandBookings.forEach(bookingCommand ->
                IntStream.range(0, bookingCommand.getNumberOfAccommodations()).forEach(i ->
                        reservation.addBooking(
                                bookingCommand.getAccommodationTypeId(),
                                bookingCommand.getStartDate(),
                                bookingCommand.getEndDate(),
                                bookingCommand.getNumberOfAdults(),
                                bookingCommand.getNumberOfChildren()
                        )
                ));
    }
}
