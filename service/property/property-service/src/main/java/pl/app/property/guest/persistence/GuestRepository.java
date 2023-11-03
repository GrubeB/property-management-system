package pl.app.property.guest.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.guest.model.GuestEntity;

import java.util.UUID;

@Repository
public interface GuestRepository extends
        JpaRepository<GuestEntity, UUID>,
        JpaSpecificationExecutor<GuestEntity> {
}
