package pl.app.property.accommodation_price_policy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;
import pl.app.property.accommodation_price.application.domain.model.PriceChangeType;

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
@Table(name = "t_accommodation_type_price_number_of_days_policy_item")
public class AccommodationTypePriceNumberOfDaysPolicyItemEntity extends AbstractEntity<UUID> implements
        RootAware<AccommodationTypePriceNumberOfDaysPolicyEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;
    @Column(name = "number_of_days")
    private Integer numberOfDays;
    @Column(name = "constant_value")
    private BigDecimal constantValue;
    @Column(name = "percentage_value")
    private BigDecimal percentageValue;
    @Enumerated(value = EnumType.STRING)
    private PriceChangeType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "policy_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypePriceNumberOfDaysPolicyEntity policy;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public AccommodationTypePriceNumberOfDaysPolicyEntity root() {
        return policy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypePriceNumberOfDaysPolicyItemEntity that = (AccommodationTypePriceNumberOfDaysPolicyItemEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
