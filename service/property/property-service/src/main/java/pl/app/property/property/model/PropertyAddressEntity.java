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
@Table(name = "t_property_address")
public class PropertyAddressEntity extends AbstractEntity<UUID> implements
        RootAware<PropertyEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id", nullable = false)
    private UUID addressId;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String region; // region/state
    @Column(name = "zip_code")
    private String zipCode; // postal/zipcode

    @OneToOne(mappedBy = "propertyAddress", orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private PropertyDetailsEntity propertyDetails;

    @Override
    public UUID getId() {
        return addressId;
    }

    @Override
    public PropertyEntity root() {
        return propertyDetails.root();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyAddressEntity that = (PropertyAddressEntity) o;
        return addressId != null && Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
