package pl.app.property.reservation.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationEntity;
import pl.app.property.reservation.application.domain.model.ReservationStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends
        JpaRepository<ReservationEntity, UUID>,
        JpaSpecificationExecutor<ReservationEntity> {
    @Query("select r.reservationId from ReservationEntity r where r.status = ?1 and r.createdDate <= ?2 and r.property.propertyId = ?3")
    List<UUID> findByStatusAndCreatedDateLessThanEqualAndProperty_PropertyId(ReservationStatus status, Instant createdDate, UUID propertyId);
}
