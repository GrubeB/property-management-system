package pl.app.property.user.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.common.shared.authorization.PermissionLevel;
import pl.app.common.shared.authorization.PermissionName;
import pl.app.property.user.adapter.out.persistence.model.PermissionEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends
        JpaRepository<PermissionEntity, UUID> {
    @Query("select p from PermissionEntity p where p.permissionName = ?1 and p.permissionLevel = ?2")
    Optional<PermissionEntity> findByPermissionNameAndPermissionName(PermissionName permissionName, PermissionLevel permissionLevel);
}
