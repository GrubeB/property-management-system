package pl.app.property.accommodation.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation.adapter.out.persistence.model.AccommodationEntity;

import java.util.UUID;

@Repository
public interface AccommodationRepository extends
        JpaRepository<AccommodationEntity, UUID>,
        JpaSpecificationExecutor<AccommodationEntity> {
}
