package pl.app.property.payment.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.payment.adapter.out.persistence.model.StripePaymentEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StripePaymentRepository extends
        JpaRepository<StripePaymentEntity, UUID>,
        JpaSpecificationExecutor<StripePaymentEntity> {
    Optional<StripePaymentEntity> findByPaymentEntity_PaymentId(UUID paymentId);

}
