package pl.app.property.payment.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.property.property.model.PropertyEntity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_wallet", uniqueConstraints = {
        @UniqueConstraint(name = "uc_walletentity_account_id", columnNames = {"account_id"})
})
public class WalletEntity extends AbstractEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id", nullable = false)
    private UUID walletId;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    @Override
    public UUID getId() {
        return walletId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WalletEntity that = (WalletEntity) o;
        return walletId != null && Objects.equals(walletId, that.walletId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
