package pl.app.property.payment.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;
import pl.app.property.payment.application.domain.model.PaymentDomainObjectType;
import pl.app.property.payment.application.domain.model.PaymentOrderStatus;

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
@Table(name = "t_payment_order")
public class PaymentOrderEntity extends AbstractEntity<UUID> implements
        RootAware<PaymentEntity> {
    @Id
    @Column(name = "payment_order_id", nullable = false)
    private UUID paymentOrderId;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private String current;
    @Enumerated(value = EnumType.STRING)
    private PaymentOrderStatus status;

    @Column(name = "payment_name", nullable = false)
    private String paymentName;

    @Column(name = "domain_object_id")
    private UUID domainObjectId;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "domain_object_type")
    private PaymentDomainObjectType domainObjectType;

    @Column(name = "is_ledger_updated", nullable = false)
    private Boolean isLedgerUpdated;
    @Column(name = "is_wallet_updated", nullable = false)
    private Boolean isWalletUpdated;

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_entity_payment_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PaymentEntity payment;

    @Override
    public UUID getId() {
        return paymentOrderId;
    }

    @Override
    public PaymentEntity root() {
        return payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentOrderEntity that = (PaymentOrderEntity) o;
        return paymentOrderId != null && Objects.equals(paymentOrderId, that.paymentOrderId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
