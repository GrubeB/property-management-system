package pl.app.property.amenity.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.app.common.core.model.AbstractEntity;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_property_amenity")
public class PropertyAmenityEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "property_amenity_id")
    private UUID propertyAmenityId;
    @Column(name = "property_id")
    private UUID propertyId;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.DETACH,
    })
    @JoinColumn(name = "amenity_id", updatable = false)
    @Fetch(FetchMode.SELECT)
    private AmenityEntity amenity;

    @Override
    public UUID getId() {
        return propertyAmenityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyAmenityEntity that = (PropertyAmenityEntity) o;
        return propertyAmenityId != null && Objects.equals(propertyAmenityId, that.propertyAmenityId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
