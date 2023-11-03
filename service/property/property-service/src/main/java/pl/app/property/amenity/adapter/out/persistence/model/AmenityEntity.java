package pl.app.property.amenity.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;

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
@Table(name = "t_amenity")
public class AmenityEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "amenity_id", nullable = false)
    private UUID amenityId;
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_name")
    private AmenityCategoryEntity amenityCategoryEntity;
    @Column(nullable = false)
    private String description;
    private Boolean standard;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "amenityEntity",
            cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    @Builder.Default
    private Set<OrganizationAmenityEntity> organizationAmenityEntities = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "amenityEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    @Builder.Default
    private Set<PropertyAmenityEntity> propertyAmenityEntities = new LinkedHashSet<>();

    @Override
    public UUID getId() {
        return amenityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AmenityEntity that = (AmenityEntity) o;
        return amenityId != null && Objects.equals(amenityId, that.amenityId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
