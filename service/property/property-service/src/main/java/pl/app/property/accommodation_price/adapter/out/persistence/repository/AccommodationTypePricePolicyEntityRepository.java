package pl.app.property.accommodation_price.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePricePolicyEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccommodationTypePricePolicyEntityRepository extends
        JpaRepository<AccommodationTypePricePolicyEntity, UUID> {
    @Query("select a from AccommodationTypePricePolicyEntity a where a.propertyId = ?1")
    List<AccommodationTypePricePolicyEntity> findByPropertyId(UUID propertyId);
}
