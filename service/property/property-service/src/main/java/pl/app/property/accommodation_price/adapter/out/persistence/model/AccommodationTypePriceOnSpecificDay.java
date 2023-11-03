package pl.app.property.accommodation_price.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type_price_specific_day")
public class AccommodationTypePriceOnSpecificDay extends AbstractEntity<UUID> implements
        RootAware<AccommodationTypePriceEntity> {
    @Id
    @Column(name = "price_id", nullable = false)
    private UUID priceId;
    private LocalDate date;
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_type_price", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypePriceEntity accommodationTypePriceEntity;

    @Override
    public UUID getId() {
        return priceId;
    }

    @Override
    public AccommodationTypePriceEntity root() {
        return accommodationTypePriceEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypePriceOnSpecificDay that = (AccommodationTypePriceOnSpecificDay) o;
        return priceId != null && Objects.equals(priceId, that.priceId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}