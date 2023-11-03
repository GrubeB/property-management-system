package pl.app.property.payment.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.payment.adapter.out.persistence.model.WalletEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends
        JpaRepository<WalletEntity, UUID> {
    @Query("select w from WalletEntity w where w.accountId = ?1")
    Optional<WalletEntity> findByAccountId(UUID accountId);
}
