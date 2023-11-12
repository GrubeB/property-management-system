package pl.app.property.property.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_property_details")
public class PropertyDetailsEntity extends AbstractEntity<UUID> implements
        RootAware<PropertyEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "property_details_id", nullable = false)
    private UUID propertyDetailsId;
    private String name;
    private String email;
    private String phone;
    private String website;
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false)
    private PropertyAddressEntity propertyAddress;

    @OneToOne(mappedBy = "propertyDetails", orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    public void setPropertyAddress(PropertyAddressEntity propertyAddress) {
        this.propertyAddress = null;
        if (propertyAddress != null) {
            propertyAddress.setPropertyDetails(this);
            this.propertyAddress = propertyAddress;
        }

    }

    @Override
    public UUID getId() {
        return propertyDetailsId;
    }

    @Override
    public PropertyEntity root() {
        return property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyDetailsEntity that = (PropertyDetailsEntity) o;
        return propertyDetailsId != null && Objects.equals(propertyDetailsId, that.propertyDetailsId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
