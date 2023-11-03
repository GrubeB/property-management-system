package pl.app.property.property.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.property.model.PropertyEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropertyRepository extends
        JpaRepository<PropertyEntity, UUID>,
        JpaSpecificationExecutor<PropertyEntity> {
    @Query("select p.propertyId from PropertyEntity p")
    List<UUID> findIdAll();
}
