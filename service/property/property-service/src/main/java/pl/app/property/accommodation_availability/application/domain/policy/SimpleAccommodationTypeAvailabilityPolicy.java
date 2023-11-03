package pl.app.property.accommodation_availability.application.domain.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationReservation;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationType;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeReservation;
import pl.app.property.accommodation_type.service.AccommodationTypeService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// it must be only one accommodation
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SimpleAccommodationTypeAvailabilityPolicy implements AccommodationTypeAvailabilityPolicy {

    private final AccommodationTypeService accommodationTypeService;

    @Override
    public boolean isAccommodationTypeAvailable(AccommodationTypeAvailability accommodationTypeAvailability, Integer numberOdAccommodations, LocalDate startDate, LocalDate endDate) {
        final List<AccommodationType.Accommodation> accommodations = accommodationTypeAvailability.getAccommodations();
        final List<AccommodationReservation> assignedReservation = accommodationTypeAvailability.getAssignedReservationsInRange(startDate, endDate);
        final List<AccommodationTypeReservation> noAssignedReservations = accommodationTypeAvailability.getNoAssignedReservationsInRange(startDate, endDate);

        List<AccommodationType.Accommodation> possibleAccommodationsToReserve = getPossibleAccommodationsToReserveBasedOnAssignedReservations(accommodations, assignedReservation);
        if (possibleAccommodationsToReserve.size() == 0) {
            return false;
        }
        Map<LocalDate, Integer> numberOfReservationOnSpecificDay = getNumberOfReservationOnSpecificDayBasedOnAssignedAndNoAssignedReservations(assignedReservation, noAssignedReservations);
        return numberOfReservationOnSpecificDay.values().stream().allMatch(numberOfReservations -> numberOfReservations + numberOdAccommodations <= accommodations.size());
    }

    private Map<LocalDate, Integer> getNumberOfReservationOnSpecificDayBasedOnAssignedAndNoAssignedReservations(List<AccommodationReservation> assignedReservation,
                                                                                                                List<AccommodationTypeReservation> noAssignedReservations) {
        Map<LocalDate, Integer> numberOfReservationOnSpecificDay = new HashMap<>(); // date, numberOfReservations
        assignedReservation.forEach(reservation -> {
            LocalDate reservationStartDate = reservation.getStartDate();
            LocalDate reservationEndDate = reservation.getEndDate();
            while (!reservationStartDate.isAfter(reservationEndDate)) {
                Integer integer = numberOfReservationOnSpecificDay.computeIfAbsent(reservationStartDate, k -> 0);
                numberOfReservationOnSpecificDay.put(reservationStartDate, integer + 1);
                reservationStartDate = reservationStartDate.plusDays(1);
            }
        });
        noAssignedReservations.forEach(reservation -> {
            LocalDate reservationStartDate = reservation.getStartDate();
            LocalDate reservationEndDate = reservation.getEndDate();
            while (!reservationStartDate.isAfter(reservationEndDate)) {
                Integer integer = numberOfReservationOnSpecificDay.computeIfAbsent(reservationStartDate, k -> 0);
                numberOfReservationOnSpecificDay.put(reservationStartDate, integer + 1);
                reservationStartDate = reservationStartDate.plusDays(1);
            }
        });
        return numberOfReservationOnSpecificDay;
    }

    private List<AccommodationType.Accommodation> getPossibleAccommodationsToReserveBasedOnAssignedReservations(List<AccommodationType.Accommodation> accommodations,
                                                                                                                List<AccommodationReservation> assignedReservation) {
        return accommodations.stream()
                .filter(accommodation -> assignedReservation.stream().noneMatch(reservation -> reservation.getAccommodationId().equals(accommodation.getAccommodationId())))
                .toList();
    }
}

