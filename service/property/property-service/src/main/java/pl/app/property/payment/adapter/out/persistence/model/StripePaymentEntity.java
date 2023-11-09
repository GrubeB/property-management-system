package pl.app.property.payment.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import pl.app.common.core.model.AbstractEntity;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_payment_stripe")
public class StripePaymentEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "stripe_payment_id", nullable = false)
    private UUID stripePaymentId;
    @Column(name = "payment_intent")
    private String paymentIntent;
    @Column(name = "session_checkout_id")
    private String sessionCheckoutId;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentEntity paymentEntity;

    @Override
    public UUID getId() {
        return stripePaymentId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        StripePaymentEntity that = (StripePaymentEntity) o;
        return getStripePaymentId() != null && Objects.equals(getStripePaymentId(), that.getStripePaymentId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
