package pl.app.property.reservation_payment_policy.adapter.out.persistence.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.model.ReservationPaymentPolicyEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationPaymentPolicyRepository extends
        JpaRepository<ReservationPaymentPolicyEntity, UUID>,
        JpaSpecificationExecutor<ReservationPaymentPolicyEntity> {
    @Query("select r from ReservationPaymentPolicyEntity r where r.property.propertyId = ?1")
    Optional<ReservationPaymentPolicyEntity> findByProperty_PropertyId(UUID propertyId);
}
