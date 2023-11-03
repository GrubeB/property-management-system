package pl.app.property.accommodation_availability.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeAvailabilityEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccommodationTypeAvailabilityEntityRepository extends
        JpaRepository<AccommodationTypeAvailabilityEntity, UUID>,
        JpaSpecificationExecutor<AccommodationTypeAvailabilityEntity> {
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationTypeAvailabilityId(UUID accommodationTypeAvailabilityId);

    @Query("select a from AccommodationTypeAvailabilityEntity a where a.accommodationType.accommodationTypeId = ?1")
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationType_AccommodationTypeId(UUID accommodationTypeId);

    @Query("""
            select a from AccommodationTypeAvailabilityEntity a inner join a.accommodationType.accommodation accommodation
            where accommodation.accommodationId = ?1""")
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationType_Accommodation_AccommodationId(UUID accommodationId);

    @Query("""
            select a from AccommodationTypeAvailabilityEntity a inner join a.accommodationReservation accommodationReservation
            where accommodationReservation.accommodationReservationId = ?1""")
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationReservation_AccommodationReservationId(UUID accommodationReservationId);

    @Query("""
            select a from AccommodationTypeAvailabilityEntity a inner join a.accommodationTypeReservation accommodationTypeReservation
            where accommodationTypeReservation.accommodationTypeReservationId = ?1""")
    Optional<AccommodationTypeAvailabilityEntity> findByAccommodationTypeReservation_AccommodationTypeReservationId(UUID accommodationTypeReservationId);


}
