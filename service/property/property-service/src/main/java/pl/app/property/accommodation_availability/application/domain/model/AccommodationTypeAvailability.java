package pl.app.property.accommodation_availability.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.common.util.DateUtils;
import pl.app.property.accommodation_availability.application.domain.exception.AccommodationAvailabilityException;
import pl.app.property.accommodation_availability.application.domain.policy.AccommodationAssignmentPolicy;
import pl.app.property.accommodation_availability.application.domain.policy.AccommodationTypeAvailabilityPolicy;
import pl.app.property.accommodation_availability.application.port.in.ManualAssignAccommodationTypeReservationCommand;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
public class AccommodationTypeAvailability {
    private UUID typeAvailabilityId;
    private AccommodationType type;
    private List<AccommodationReservation> reservations;
    private List<AccommodationTypeReservation> typeReservations;
    private LocalDate startDate;
    private LocalDate endDate;

    public AccommodationTypeAvailability(AccommodationTypeEntity entity) {
        this.typeAvailabilityId = UUID.randomUUID();

        List<AccommodationType.Accommodation> accommodations = entity.getAccommodation() == null ? Collections.emptyList() : entity.getAccommodation().stream()
                .map(acc -> new AccommodationType.Accommodation(acc.getAccommodationId()))
                .collect(Collectors.toList());

        this.type = new AccommodationType(entity.getAccommodationTypeId(), new AccommodationType.Property(entity.getProperty().getPropertyId()), accommodations);
        this.reservations = new ArrayList<>();
        this.typeReservations = new ArrayList<>();
    }


    public void verifyAccommodationAvailability(UUID accommodationId, LocalDate startDate, LocalDate endDate) {
        if (!DateUtils.isBefore(startDate, endDate)) {
            throw new AccommodationAvailabilityException.AccommodationReservationValidationException("Invalid data range in request");
        }
        if (!this.reservations.stream()
                .filter(reservation -> reservation.getAccommodationId().equals(accommodationId))
                .allMatch(reservation -> DateUtils.isDataRangeNoCollide(startDate, endDate, reservation.getStartDate(), reservation.getEndDate()))) {
            throw AccommodationAvailabilityException.NoAccommodationAvailableException.fromId(accommodationId);
        }
    }

    public void verifyAccommodationTypeAvailability(AccommodationTypeAvailabilityPolicy policy, Integer numberOdAccommodations, LocalDate startDate, LocalDate endDate) {
        if (!DateUtils.isBefore(startDate, endDate)) {
            throw new AccommodationAvailabilityException.AccommodationReservationValidationException("Invalid data range in request");
        }
        if (!policy.isAccommodationTypeAvailable(this, numberOdAccommodations, startDate, endDate)) {
            throw new AccommodationAvailabilityException.NoAccommodationAvailableException();
        }
    }

    public void verifyAccommodationTypeAvailabilityForTypeReservation(AccommodationTypeAvailabilityPolicy policy, AccommodationTypeReservation typeReservation) {
        if (!DateUtils.isBefore(typeReservation.getStartDate(), typeReservation.getEndDate())) {
            throw new AccommodationAvailabilityException.AccommodationReservationValidationException("Invalid data range in request");
        }
        if (!policy.isAccommodationTypeAvailable(this, 0, startDate, endDate)) {
            throw new AccommodationAvailabilityException.NoAccommodationAvailableException();
        }
    }

    public AccommodationReservation createReservation(UUID accommodationId, AccommodationStatus status, LocalDate startDate, LocalDate endDate) {
        AccommodationReservation newAccommodationReservation = new AccommodationReservation(accommodationId, status, startDate, endDate);
        addReservation(newAccommodationReservation);
        return newAccommodationReservation;
    }

    public AccommodationTypeReservation createTypeReservation(LocalDate startDate, LocalDate endDate) {
        AccommodationTypeReservation newAccommodationTypeReservation = new AccommodationTypeReservation(startDate, endDate, this.type.getAccommodationTypeId());
        addTypeReservation(newAccommodationTypeReservation);
        return newAccommodationTypeReservation;
    }


    public void tryToAutoAssignTypeReservation(AccommodationAssignmentPolicy policy, AccommodationTypeReservation newTypeReservation) {
        List<AccommodationReservation> newReservations = policy.reserveAccommodation(this, 1, newTypeReservation.getStartDate(), newTypeReservation.getEndDate());
        if (newReservations.size() > 0) {
            newReservations.forEach(newReservation -> addReservationForTypeReservation(newTypeReservation, newReservation));
            newTypeReservation.setAssignedStatus(AccommodationAssignedStatus.AUTO_ASSIGNED);
        }
    }

    public void tryToManualAssignTypeReservations(AccommodationTypeReservation typeReservation, Set<ManualAssignAccommodationTypeReservationCommand.AccommodationReservation> newAccommodationReservations) {
        removeAllReservationsForTypeReservation(typeReservation);
        typeReservation.setAssignedStatus(AccommodationAssignedStatus.NO_ASSIGNED);
        newAccommodationReservations.forEach(newReservation -> {
            this.verifyAccommodationAvailability(newReservation.getAccommodationId(), newReservation.getStartDate(), newReservation.getEndDate());
            AccommodationReservation reservation = new AccommodationReservation(newReservation.getAccommodationId(), AccommodationStatus.RESERVED, newReservation.getStartDate(), newReservation.getEndDate());
            addReservationForTypeReservation(typeReservation, reservation);
        });
        typeReservation.setAssignedStatus(AccommodationAssignedStatus.MANUAL_ASSIGNED);
    }

    // ADD/REMOVE RESERVATION
    private void removeAllReservationsForTypeReservation(AccommodationTypeReservation typeReservation) {
        List<AccommodationReservation> removedReservations = typeReservation.removeAllReservations();
        removedReservations.forEach(this::removeReservation);
    }

    public void addReservationForTypeReservation(AccommodationTypeReservation typeReservation, AccommodationReservation reservation) {
        typeReservation.addReservation(reservation);
        this.reservations.add(reservation);
    }

    public void addReservation(AccommodationReservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeReservation(AccommodationReservation reservation) {
        this.reservations.remove(reservation);
    }

    public void removeReservationById(UUID accommodationReservationId) {
        this.reservations.removeIf(reservations -> reservations.getAccommodationReservationId().equals(accommodationReservationId));
    }

    public void addTypeReservation(AccommodationTypeReservation typeReservation) {
        this.typeReservations.add(typeReservation);
    }

    // ADD/REMOVE TYPE RESERVATION
    public void removeTypeReservation(AccommodationTypeReservation typeReservation) {
        unassignTypeReservations(typeReservation);
        this.typeReservations.remove(typeReservation);
    }

    public void removeTypeReservationById(UUID accommodationTypeReservationId) {
        AccommodationTypeReservation typeReservation = getAccommodationTypeReservationById(accommodationTypeReservationId);
        unassignTypeReservations(typeReservation);
        this.typeReservations.remove(typeReservation);
    }

    public void unassignTypeReservations(AccommodationTypeReservation accommodationTypeReservation) {
        removeAllReservationsForTypeReservation(accommodationTypeReservation);
        accommodationTypeReservation.setAssignedStatus(AccommodationAssignedStatus.NO_ASSIGNED);
    }

    // GETTERS
    public AccommodationTypeReservation getAccommodationTypeReservationById(UUID typeReservationId) {
        return this.typeReservations.stream()
                .filter(reservation -> typeReservationId.equals(reservation.getTypeReservationId()))
                .findAny().orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeReservationException.fromId(typeReservationId));
    }

    public List<AccommodationType.Accommodation> getAccommodations() {
        return this.getType().getAccommodation();
    }

    public List<AccommodationReservation> getAssignedReservations() {
        return this.reservations;
    }

    public List<AccommodationReservation> getAssignedReservationsInRange(LocalDate startDate, LocalDate endDate) {
        return this.reservations.stream()
                .filter(reservation -> DateUtils.isDataRangeCollide(reservation.getStartDate(), reservation.getEndDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    public List<AccommodationTypeReservation> getNoAssignedReservations() {
        return this.typeReservations.stream()
                .filter(Predicate.not(AccommodationTypeReservation::isAssigned))
                .collect(Collectors.toList());
    }

    public List<AccommodationTypeReservation> getNoAssignedReservationsInRange(LocalDate startDate, LocalDate endDate) {
        return this.getNoAssignedReservations().stream()
                .filter(typeReservation -> DateUtils.isDataRangeCollide(typeReservation.getStartDate(), typeReservation.getEndDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
