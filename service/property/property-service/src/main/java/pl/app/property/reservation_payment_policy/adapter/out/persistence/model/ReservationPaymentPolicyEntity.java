package pl.app.property.reservation_payment_policy.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.ParentEntity;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicyType;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_reservation_payment_policy",
        uniqueConstraints = {@UniqueConstraint(name = "uc_t_reservation_payment_policy_property_id", columnNames = {"property_id"})})
public class ReservationPaymentPolicyEntity extends AbstractEntity<UUID> implements
        ParentEntity<PropertyEntity> {
    @Id
    @Column(name = "policy_id", nullable = false)
    private UUID policyId;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ReservationPaymentPolicyType type;
    @Column(name = "fixed_value", nullable = false)
    private BigDecimal fixedValue;
    @Column(name = "number_of_days_before_registration", nullable = false)
    private Integer numberOfDaysBeforeRegistration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    @Override
    public UUID getId() {
        return policyId;
    }

    @Override
    public PropertyEntity getParent() {
        return property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReservationPaymentPolicyEntity that = (ReservationPaymentPolicyEntity) o;
        return policyId != null && Objects.equals(policyId, that.policyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
