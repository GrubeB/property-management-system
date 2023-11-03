package pl.app.property.amenity.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.amenity.adapter.out.persistence.model.AmenityEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface AmenityEntityRepository extends
        JpaRepository<AmenityEntity, UUID>,
        JpaSpecificationExecutor<AmenityEntity> {
    List<AmenityEntity> findByStandardTrue();
}