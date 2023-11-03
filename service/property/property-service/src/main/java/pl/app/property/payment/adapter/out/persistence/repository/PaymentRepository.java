package pl.app.property.payment.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.payment.adapter.out.persistence.model.PaymentEntity;

import java.util.UUID;

@Repository
public interface PaymentRepository extends
        JpaRepository<PaymentEntity, UUID>,
        JpaSpecificationExecutor<PaymentEntity> {
}
