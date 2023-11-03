package pl.app.property.amenity.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.amenity.adapter.out.persistence.model.AmenityCategoryEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmenityCategoryEntityRepository extends
        JpaRepository<AmenityCategoryEntity, UUID> {
    @Query("select a from AmenityCategoryEntity a where a.name = ?1")
    Optional<AmenityCategoryEntity> findByName(String name);
}