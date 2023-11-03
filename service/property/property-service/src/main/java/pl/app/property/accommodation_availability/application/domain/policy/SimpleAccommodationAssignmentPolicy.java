package pl.app.property.accommodation_availability.application.domain.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationReservation;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationStatus;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationType;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_type.service.AccommodationTypeService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// try to reserve only one accommodation
// policy may return 0 or 1 reservation
@Service
@RequiredArgsConstructor
@Transactional
class SimpleAccommodationAssignmentPolicy implements AccommodationAssignmentPolicy {

    private final AccommodationTypeService accommodationTypeService;

    @Override
    public List<AccommodationReservation> reserveAccommodation(AccommodationTypeAvailability accommodationTypeAvailability, Integer numberOdAccommodations, LocalDate startDate, LocalDate endDate) {
        List<AccommodationType.Accommodation> accommodations = accommodationTypeAvailability.getAccommodations();
        List<AccommodationReservation> assignedReservation = accommodationTypeAvailability.getAssignedReservationsInRange(startDate, endDate);

        Optional<AccommodationType.Accommodation> possibleAccommodationToReserve = getPossibleAccommodationToReserveBasedOnAssignedReservations(accommodations, assignedReservation);
        if (possibleAccommodationToReserve.isEmpty()) {
            return Collections.emptyList();
        }
        // reserve accommodation
        return List.of(new AccommodationReservation(possibleAccommodationToReserve.get().getAccommodationId(), AccommodationStatus.RESERVED, startDate, endDate));
    }

    private Optional<AccommodationType.Accommodation> getPossibleAccommodationToReserveBasedOnAssignedReservations(List<AccommodationType.Accommodation> accommodations, List<AccommodationReservation> assignedReservation) {
        return accommodations.stream()
                .filter(accommodation -> assignedReservation.stream().noneMatch(reservation -> reservation.getAccommodationId().equals(accommodation.getAccommodationId())))
                .findAny();
    }
}
