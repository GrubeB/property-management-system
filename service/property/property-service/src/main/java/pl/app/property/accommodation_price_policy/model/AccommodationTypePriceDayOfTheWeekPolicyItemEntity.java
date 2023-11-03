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
import java.time.DayOfWeek;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type_price_day_of_the_week_policy_item")
public class AccommodationTypePriceDayOfTheWeekPolicyItemEntity extends AbstractEntity<UUID> implements
        RootAware<AccommodationTypePriceDayOfTheWeekPolicyEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;
    @Column(name = "constant_value", nullable = false)
    private BigDecimal constantValue;
    @Column(name = "percentage_value", nullable = false)
    private BigDecimal percentageValue;
    @Column(name = "day_of_week", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @Enumerated(value = EnumType.STRING)
    private PriceChangeType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "policy_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypePriceDayOfTheWeekPolicyEntity policy;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public AccommodationTypePriceDayOfTheWeekPolicyEntity root() {
        return policy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypePriceDayOfTheWeekPolicyItemEntity that = (AccommodationTypePriceDayOfTheWeekPolicyItemEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
