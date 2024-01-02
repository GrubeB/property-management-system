package pl.app.property.property.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.ParentEntity;
import pl.app.property.organization.model.OrganizationEntity;

import java.time.LocalTime;
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
@Table(name = "t_property")
public class PropertyEntity extends AbstractEntity<UUID> implements
        ParentEntity<OrganizationEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "property_id", nullable = false)
    private UUID propertyId;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PropertyType propertyType;
    @Column(name = "check_in_from_time")
    private LocalTime checkInFromTime;
    @Column(name = "check_in_to_time")
    private LocalTime checkInToTime;
    @Column(name = "check_out_from_time")
    private LocalTime checkOutFromTime;
    @Column(name = "check_out_to_time")
    private LocalTime checkOutToTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<PropertyImageEntity> propertyImages = new LinkedHashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "property_details_id")
    private PropertyDetailsEntity propertyDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private OrganizationEntity organization;

    public void setPropertyImages(Set<PropertyImageEntity> propertyImages) {
        this.propertyImages.clear();
        if (propertyImages != null) {
            propertyImages.stream()
                    .peek(ch -> ch.setProperty(this))
                    .forEach(this.propertyImages::add);
        }
    }

    public void setPropertyDetails(PropertyDetailsEntity propertyDetails) {
        this.propertyDetails = null;
        if (propertyDetails != null) {
            propertyDetails.setProperty(this);
            this.propertyDetails = propertyDetails;
        }
    }

    @Override
    public UUID getId() {
        return propertyId;
    }

    @Override
    public OrganizationEntity getParent() {
        return organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyEntity that = (PropertyEntity) o;
        return propertyId != null && Objects.equals(propertyId, that.propertyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
