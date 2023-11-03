package pl.app.property.accommodation_availability.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeReservationEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccommodationTypeReservationEntityRepository extends
        JpaRepository<AccommodationTypeReservationEntity, UUID> {
    @Query("""
            select a from AccommodationTypeReservationEntity a
            where a.accommodationTypeId = ?1 and a.startDate >= ?2 and a.endDate <= ?3""")
    List<AccommodationTypeReservationEntity> findByAccommodationTypeIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(UUID accommodationTypeId, LocalDate startDate, LocalDate endDate);

}
