package pl.app.property.accommodation_price_policy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.ParentEntity;
import pl.app.property.property.model.PropertyEntity;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type_price_number_of_days_policy")
public class AccommodationTypePriceNumberOfDaysPolicyEntity extends AbstractEntity<UUID> implements
        ParentEntity<PropertyEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "policy_id", nullable = false)
    private UUID policyId;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "policy",
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationTypePriceNumberOfDaysPolicyItemEntity> items = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_Id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;


    public void setItems(Set<AccommodationTypePriceNumberOfDaysPolicyItemEntity> items) {
        this.items.clear();
        items.stream()
                .peek(e -> e.setPolicy(this))
                .forEach(this.items::add);
    }

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
        AccommodationTypePriceNumberOfDaysPolicyEntity that = (AccommodationTypePriceNumberOfDaysPolicyEntity) o;
        return getPolicyId() != null && Objects.equals(getPolicyId(), that.getPolicyId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
