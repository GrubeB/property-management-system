package pl.app.property.accommodation_price.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.property.accommodation_price.application.domain.policy.AccommodationTypePricePolicyType;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type_price_policy")
public class AccommodationTypePricePolicyEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "accommodation_type_price_policy_id", nullable = false)
    private UUID accommodationTypePricePolicyId;
    @Column(name = "property_id", nullable = false)
    private UUID propertyId;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "policy_type", nullable = false)
    private AccommodationTypePricePolicyType policyType;
    @Column(name = "is_active")
    private Boolean isActive;

    @Override
    public UUID getId() {
        return accommodationTypePricePolicyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypePricePolicyEntity that = (AccommodationTypePricePolicyEntity) o;
        return accommodationTypePricePolicyId != null && Objects.equals(accommodationTypePricePolicyId, that.accommodationTypePricePolicyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
