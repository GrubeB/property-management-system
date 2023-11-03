package pl.app.property.accommodation_price.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePriceEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccommodationTypePriceEntityRepository extends
        JpaRepository<AccommodationTypePriceEntity, UUID>,
        JpaSpecificationExecutor<AccommodationTypePriceEntity> {
    @Query("select a from AccommodationTypePriceEntity a where a.accommodationType.accommodationTypeId = ?1")
    Optional<AccommodationTypePriceEntity> findByAccommodationType_AccommodationTypeId(UUID accommodationTypeId);
}
