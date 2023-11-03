package pl.app.property.payment.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.property.property.model.PropertyEntity;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_payment")
public class PaymentEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "payment_id", nullable = false)
    private UUID paymentId;
    @Column(name = "buyer_id")
    private UUID buyerId;
    @Column(name = "seller_id")
    private UUID sellerId;
    @Column(name = "is_payment_done")
    private Boolean isPaymentDone;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "payment",
            orphanRemoval = true)
    @Builder.Default
    private Set<PaymentOrderEntity> paymentOrders = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    @Override
    public UUID getId() {
        return paymentId;
    }

    public void setPaymentOrders(Set<PaymentOrderEntity> paymentOrders) {
        this.paymentOrders.clear();
        paymentOrders.stream()
                .peek(o -> o.setPayment(this))
                .forEach(this.paymentOrders::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentEntity payment = (PaymentEntity) o;
        return paymentId != null && Objects.equals(paymentId, payment.paymentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
