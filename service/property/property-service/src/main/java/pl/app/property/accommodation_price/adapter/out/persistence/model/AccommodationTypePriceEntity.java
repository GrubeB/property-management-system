package pl.app.property.accommodation_price.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.ParentEntity;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;

import java.math.BigDecimal;
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
@Table(name = "t_accommodation_type_price")
public class AccommodationTypePriceEntity extends AbstractEntity<UUID> implements
        ParentEntity<AccommodationTypeEntity> {
    @Id
    @Column(name = "accommodation_type_price_id", nullable = false)
    private UUID accommodationTypePriceId;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accommodation_type", nullable = false)
    private AccommodationTypeEntity accommodationType;

    @Column(name = "default_price_per_day", nullable = false)
    private BigDecimal defaultPricePerDay;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "accommodationTypePriceEntity",
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationTypePriceOnSpecificDay> accommodationTypePriceOnSpecificDays = new LinkedHashSet<>();

    @Override
    public UUID getId() {
        return accommodationTypePriceId;
    }

    @Override
    public AccommodationTypeEntity getParent() {
        return accommodationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypePriceEntity that = (AccommodationTypePriceEntity) o;
        return accommodationTypePriceId != null && Objects.equals(accommodationTypePriceId, that.accommodationTypePriceId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
