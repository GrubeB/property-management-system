package pl.app.property.accommodation_price_policy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccommodationTypePriceNumberOfDaysPolicyRepository extends
        JpaRepository<AccommodationTypePriceNumberOfDaysPolicyEntity, UUID>,
        JpaSpecificationExecutor<AccommodationTypePriceNumberOfDaysPolicyEntity> {
    @Query("select a from AccommodationTypePriceNumberOfDaysPolicyEntity a where a.property.propertyId = ?1")
    Optional<AccommodationTypePriceNumberOfDaysPolicyEntity> findByPropertyId(UUID propertyId);

}
