package pl.app.property.accommodation_availability.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationReservationEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccommodationReservationEntityRepository extends
        JpaRepository<AccommodationReservationEntity, UUID> {
    @Query("""
            select a from AccommodationReservationEntity a
            where a.accommodation.accommodationType.accommodationTypeId = ?1 and a.startDate >= ?2 and a.endDate <= ?3""")
    List<AccommodationReservationEntity> findByAccommodation_AccommodationType_AccommodationTypeIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(UUID accommodationTypeId, LocalDate startDate, LocalDate endDate);
}
