package pl.app.property.reservation_folio.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioEntity;

import java.util.UUID;

@Repository
public interface ReservationFolioRepository extends
        JpaRepository<ReservationFolioEntity, UUID>,
        JpaSpecificationExecutor<ReservationFolioEntity> {
}
