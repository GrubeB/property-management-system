package pl.app.property.accommodation_availability.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.accommodation.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationAssignedStatus;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationStatus;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationTypeAvailabilityDto implements Serializable {
    private UUID accommodationTypeAvailabilityId;
    private AccommodationTypeEntity accommodationType;
    private Set<AccommodationReservationDto> accommodationReservation;
    private Set<AccommodationTypeReservationDto> accommodationTypeReservation;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationReservationDto implements Serializable {
        private UUID accommodationReservationId;
        private AccommodationEntity accommodation;
        private AccommodationStatus status;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationTypeReservationDto implements Serializable {
        private UUID accommodationTypeReservationId;
        private LocalDate startDate;
        private LocalDate endDate;
        private AccommodationAssignedStatus status;
        private Set<AccommodationReservationDto> accommodationReservationEntities;
    }
}
