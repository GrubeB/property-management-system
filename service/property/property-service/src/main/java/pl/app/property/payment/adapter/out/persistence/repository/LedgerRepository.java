package pl.app.property.payment.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.property.payment.adapter.out.persistence.model.LedgerEntity;

import java.util.UUID;

@Repository
public interface LedgerRepository extends
        JpaRepository<LedgerEntity, UUID> {
}
