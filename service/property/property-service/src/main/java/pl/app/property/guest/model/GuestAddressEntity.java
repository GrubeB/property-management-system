package pl.app.property.guest.model;

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
@Table(name = "t_guest_address")
public class GuestAddressEntity extends AbstractEntity<UUID> implements RootAware<GuestEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id", nullable = false)
    private UUID addressId;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String region;
    @Column(name = "zip_code")
    private String zipCode;

    @OneToOne(mappedBy = "address", orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private GuestEntity guest;

    @Override
    public UUID getId() {
        return addressId;
    }

    @Override
    public GuestEntity root() {
        return guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GuestAddressEntity that = (GuestAddressEntity) o;
        return addressId != null && Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
