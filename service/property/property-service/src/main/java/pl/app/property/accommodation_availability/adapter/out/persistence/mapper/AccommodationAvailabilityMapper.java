package pl.app.property.accommodation_availability.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.accommodation.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation.adapter.out.query.AccommodationQueryService;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationReservationEntity;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeReservationEntity;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationReservation;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationType;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeReservation;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.service.AccommodationTypeQueryService;
import pl.app.property.property.model.PropertyEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccommodationAvailabilityMapper {
    private final AccommodationQueryService accommodationQueryService;
    private final AccommodationTypeQueryService accommodationTypeQueryService;

    public AccommodationTypeAvailability mapToAccommodationTypeAvailability(AccommodationTypeAvailabilityEntity entity, LocalDate startDate, LocalDate endDate) {
        AccommodationType accommodationType = this.mapToAccommodationType(entity.getAccommodationType());
        List<AccommodationReservation> reservations = entity.getAccommodationReservation().stream().map(this::mapToAccommodationReservation).collect(Collectors.toList());
        List<AccommodationTypeReservation> typeReservations = entity.getAccommodationTypeReservation().stream()
                .map(r -> mapToAccommodationTypeReservation(r, reservations))
                .collect(Collectors.toList());
        return new AccommodationTypeAvailability(entity.getAccommodationTypeAvailabilityId(), accommodationType, reservations, typeReservations, startDate, endDate);
    }

    public AccommodationTypeAvailability mapToAccommodationTypeAvailability(UUID accommodationTypeAvailabilityId,
                                                                            AccommodationTypeEntity accommodationTypeEntity,
                                                                            List<AccommodationReservationEntity> reservationEntities,
                                                                            List<AccommodationTypeReservationEntity> typeReservationEntities,
                                                                            LocalDate startDate, LocalDate endDate) {
        AccommodationType accommodationType = this.mapToAccommodationType(accommodationTypeEntity);
        List<AccommodationReservation> reservations = reservationEntities.stream().map(this::mapToAccommodationReservation).collect(Collectors.toList());
        List<AccommodationTypeReservation> typeReservations = typeReservationEntities.stream()
                .map(r -> mapToAccommodationTypeReservation(r, reservations))
                .collect(Collectors.toList());
        return new AccommodationTypeAvailability(accommodationTypeAvailabilityId, accommodationType, reservations, typeReservations, startDate, endDate);
    }


    public AccommodationType mapToAccommodationType(AccommodationTypeEntity accommodationTypeEntity) {
        return new AccommodationType(
                accommodationTypeEntity.getAccommodationTypeId(),
                this.mapToProperty(accommodationTypeEntity.getProperty()),
                accommodationTypeEntity.getAccommodation().stream().map(this::mapToAccommodation).collect(Collectors.toList())
        );
    }

    private AccommodationType.Accommodation mapToAccommodation(AccommodationEntity accommodationEntity) {
        return new AccommodationType.Accommodation(accommodationEntity.getAccommodationId());
    }

    private AccommodationType.Property mapToProperty(PropertyEntity property) {
        return new AccommodationType.Property(property.getPropertyId());
    }

    public AccommodationReservation mapToAccommodationReservation(AccommodationReservationEntity entity) {
        return new AccommodationReservation(
                entity.getAccommodationReservationId(),
                entity.getAccommodation().getAccommodationId(),
                entity.getStatus(),
                entity.getStartDate(),
                entity.getEndDate(),
                null
        );
    }

    public AccommodationTypeReservation mapToAccommodationTypeReservation(AccommodationTypeReservationEntity entity, List<AccommodationReservation> reservations) {
        List<AccommodationReservation> accommodationReservations = entity.getAccommodationReservationEntities().stream()
                .map(AccommodationReservationEntity::getAccommodationReservationId)
                .map(id -> reservations.stream().filter(r -> r.getAccommodationReservationId().equals(id)).findAny())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        AccommodationTypeReservation accommodationTypeReservation = new AccommodationTypeReservation(
                entity.getAccommodationTypeReservationId(),
                entity.getAccommodationTypeId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getStatus(),
                accommodationReservations
        );
        accommodationTypeReservation.getReservations().forEach(r -> r.setAccommodationTypeReservation(accommodationTypeReservation));
        return accommodationTypeReservation;
    }

    public AccommodationTypeAvailabilityEntity mapToAccommodationTypeAvailabilityEntity(AccommodationTypeAvailability domain) {
        Set<AccommodationReservationEntity> reservationEntities = domain.getReservations().stream()
                .map(this::mapToAccommodationReservationEntity)
                .collect(Collectors.toSet());
        Set<AccommodationTypeReservationEntity> typeReservationEntities = domain.getTypeReservations().stream()
                .map(typeReservation -> this.mapToAccommodationTypeReservationEntity(typeReservation, reservationEntities))
                .collect(Collectors.toSet());

        AccommodationTypeAvailabilityEntity entity = AccommodationTypeAvailabilityEntity.builder()
                .accommodationTypeAvailabilityId(domain.getTypeAvailabilityId())
                .accommodationType(accommodationTypeQueryService.fetchById(domain.getType().getAccommodationTypeId()))
                .accommodationReservation(reservationEntities)
                .accommodationTypeReservation(typeReservationEntities)
                .build();

        entity.getAccommodationReservation().forEach(reservation -> reservation.setAccommodationTypeAvailabilityEntity(entity));
        entity.getAccommodationTypeReservation().forEach(typeReservation -> typeReservation.setAccommodationTypeAvailabilityEntity(entity));
        entity.getAccommodationTypeReservation().stream()
                .map(AccommodationTypeReservationEntity::getAccommodationReservationEntities)
                .flatMap(Set::stream)
                .forEach(reservation -> reservation.setAccommodationTypeAvailabilityEntity(entity));
        return entity;
    }

    private AccommodationTypeReservationEntity mapToAccommodationTypeReservationEntity(AccommodationTypeReservation domain, Set<AccommodationReservationEntity> allReservationEntities) {
        AccommodationTypeReservationEntity entity = AccommodationTypeReservationEntity.builder()
                .accommodationTypeReservationId(domain.getTypeReservationId())
                .accommodationTypeId(domain.getTypeId())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .status(domain.getAssignedStatus())
                .build();
        Set<AccommodationReservationEntity> reservationEntities = domain.getReservations().stream()
                .map(reservation -> allReservationEntities.stream().filter(reservationEntity -> reservation.getAccommodationReservationId().equals(reservationEntity.getAccommodationReservationId())).findAny())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        reservationEntities.forEach(reservationEntity -> reservationEntity.setAccommodationTypeReservationEntity(entity));
        entity.setAccommodationReservationEntities(reservationEntities);
        return entity;
    }

    public AccommodationReservationEntity mapToAccommodationReservationEntity(AccommodationReservation domain) {
        return AccommodationReservationEntity.builder()
                .accommodationReservationId(domain.getAccommodationReservationId())
                .accommodation(accommodationQueryService.fetchById(domain.getAccommodationId()))
                .status(domain.getStatus())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .build();
    }
}
