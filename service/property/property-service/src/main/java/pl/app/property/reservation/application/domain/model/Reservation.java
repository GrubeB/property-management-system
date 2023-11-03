package pl.app.property.reservation.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.property.registration.application.domain.exception.RegistrationException;
import pl.app.property.reservation.application.domain.exception.ReservationException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Reservation {
    private UUID reservationId;
    private UUID propertyId;
    private ReservationStatus status;
    private ReservationSource source;
    private ReservationGuest mainGuest;
    private List<ReservationGuest> otherGuests;
    private List<ReservationBooking> reservationBookings;
    private UUID reservationFolioId;
    private Instant createdDate;

    public Reservation(UUID propertyId, ReservationSource source, ReservationGuest mainGuest, List<ReservationGuest> otherGuests) {
        this.reservationId = UUID.randomUUID();
        this.propertyId = propertyId;
        this.status = ReservationStatus.PENDING;
        this.source = source;
        this.mainGuest = mainGuest;
        this.otherGuests = otherGuests;
        this.reservationBookings = new ArrayList<>();
        this.reservationFolioId = null;
        this.createdDate = Instant.now();
    }


    public void addBooking(UUID accommodationTypeId, LocalDate startDate, LocalDate endDate, Integer numberOfAdults, Integer numberOfChildren) {
        ReservationBooking booking = new ReservationBooking(startDate, endDate, accommodationTypeId, numberOfAdults, numberOfChildren);
        reservationBookings.add(booking);
    }

    public void setReservationFolioId(UUID reservationFolioId) {
        this.reservationFolioId = reservationFolioId;
    }

    // STATUS
    public void verifyReservationMayBeConfirmed() {
        if (!this.status.equals(ReservationStatus.PENDING)) {
            throw new RegistrationException.NotFoundRegistrationException("Only reservation in PENDING state can be confirmed");
        }
    }

    public void confirmReservation() {
        this.verifyReservationMayBeConfirmed();
        this.status = ReservationStatus.CONFIRMED;
    }

    public void verifyReservationMayBeCanceled() {
        if (!this.status.equals(ReservationStatus.CONFIRMED)) {
            throw new ReservationException.ReservationWrongStatedException("Only reservation in CONFIRMED state can be canceled");
        }
    }

    public void cancelReservation() {
        this.verifyReservationMayBeCanceled();
        this.status = ReservationStatus.CANCELED;
    }

    public void verifyReservationMayBeRejected() {
        if (!(this.status.equals(ReservationStatus.PENDING) || this.status.equals(ReservationStatus.CONFIRMED))) {
            throw new ReservationException.ReservationWrongStatedException("Only reservation in PENDING or CONFIRMED state can be rejected");
        }
    }

    public void rejectReservation() {
        this.verifyReservationMayBeRejected();
        this.status = ReservationStatus.REJECTED;
    }

    public void verifyReservationMayBeFinished() {
        if (!(this.status.equals(ReservationStatus.CONFIRMED))) {
            throw new ReservationException.ReservationWrongStatedException("Only reservation in CONFIRMED or PAID state can be finished");
        }
    }

    public void finishReservation() {
        this.verifyReservationMayBeFinished();
        this.status = ReservationStatus.FINISHED;
    }

    public boolean shouldReservationByPaid(Integer numberOfDaysBeforeRegistration) {
        Optional<LocalDate> reservationStart = this.getReservationStart();
        if (reservationStart.isEmpty()) { // there is no bookings
            return false;
        }
        if (this.createdDate.plus(1, ChronoUnit.DAYS).isAfter(Instant.now())) { // if reservation was created day before registration
            return false;
        }
        LocalDate shouldByPaidBefore = reservationStart.get().minusDays(numberOfDaysBeforeRegistration);
        return shouldByPaidBefore.isBefore(LocalDate.now());
    }

    public Optional<LocalDate> getReservationStart() {
        return this.reservationBookings.stream()
                .map(ReservationBooking::getStart)
                .min(LocalDate::compareTo);
    }
}
