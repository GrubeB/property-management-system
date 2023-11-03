package pl.app.property.accommodation_price_policy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccommodationTypePriceDayOfTheWeekPolicyRepository extends
        JpaRepository<AccommodationTypePriceDayOfTheWeekPolicyEntity, UUID>,
        JpaSpecificationExecutor<AccommodationTypePriceDayOfTheWeekPolicyEntity> {
    @Query("select a from AccommodationTypePriceDayOfTheWeekPolicyEntity a where a.property.propertyId = ?1")
    Optional<AccommodationTypePriceDayOfTheWeekPolicyEntity> findByPropertyId(UUID propertyId);
}
