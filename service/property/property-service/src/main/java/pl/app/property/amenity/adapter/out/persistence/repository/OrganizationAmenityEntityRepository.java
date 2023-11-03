package pl.app.property.amenity.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.amenity.adapter.out.persistence.model.OrganizationAmenityEntity;

import java.util.Set;
import java.util.UUID;

@Repository
public interface OrganizationAmenityEntityRepository extends
        JpaRepository<OrganizationAmenityEntity, UUID>,
        JpaSpecificationExecutor<OrganizationAmenityEntity> {
    Set<OrganizationAmenityEntity> findByOrganizationId(UUID organizationId);

}