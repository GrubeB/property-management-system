package pl.app.property.accommodation_availability.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.common.util.DateUtils;
import pl.app.property.accommodation_availability.application.domain.exception.AccommodationAvailabilityException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AccommodationTypeReservation {
    private UUID typeReservationId;
    private UUID typeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private AccommodationAssignedStatus assignedStatus;
    private List<AccommodationReservation> reservations;

    public AccommodationTypeReservation(LocalDate startDate, LocalDate endDate, UUID typeId) {
        this.typeReservationId = UUID.randomUUID();
        this.startDate = startDate;
        this.endDate = endDate;
        this.typeId = typeId;
        this.reservations = new ArrayList<>();
        this.assignedStatus = AccommodationAssignedStatus.NO_ASSIGNED;
    }

    // RESERVATION
    public List<AccommodationReservation> setReservations(List<AccommodationReservation> newReservations) {
        this.removeAllReservations();
        newReservations.forEach(this::addReservation);
        return newReservations;
    }

    public List<AccommodationReservation> removeAllReservations() {
        this.setAssignedStatus(AccommodationAssignedStatus.NO_ASSIGNED);
        List<AccommodationReservation> removedReservations = this.reservations.stream()
                .peek(reservation -> reservation.setAccommodationTypeReservation(null))
                .collect(Collectors.toList());
        this.reservations = new ArrayList<>();
        return removedReservations;
    }

    public AccommodationReservation addReservation(AccommodationReservation reservation) {
        this.validAccommodation(reservation);
        reservation.setAccommodationTypeReservation(this);
        this.reservations.add(reservation);
        return reservation;
    }

    public AccommodationReservation removeReservation(AccommodationReservation reservation) {
        reservation.setAccommodationTypeReservation(null);
        this.reservations.remove(reservation);
        return reservation;
    }

    // ASSIGNATION
    public Boolean isAssigned() {
        return !AccommodationAssignedStatus.NO_ASSIGNED.equals(this.assignedStatus);
    }

    public void setAssignedStatus(AccommodationAssignedStatus assignedStatus) {
        this.assignedStatus = assignedStatus;
    }

    // VALIDATION
    public void validAccommodation(AccommodationReservation accommodationReservation) {
        this.validAccommodationDates(accommodationReservation.getStartDate(), accommodationReservation.getEndDate());
    }

    public void validAccommodationDates(LocalDate startDate, LocalDate endDate) {
        if (!this.reservations.stream().allMatch(reservation -> DateUtils.isDataRangeNoCollide(startDate, endDate, reservation.getStartDate(), reservation.getEndDate()))) {
            throw new AccommodationAvailabilityException.AccommodationReservationValidationException("Invalid data range in request");
        }
        if (DateUtils.isBefore(startDate, this.startDate) || DateUtils.isAfter(endDate, this.endDate)) {
            throw new AccommodationAvailabilityException.AccommodationReservationValidationException("Invalid data range in request");
        }
    }
}
