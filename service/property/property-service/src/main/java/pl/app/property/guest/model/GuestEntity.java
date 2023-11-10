package pl.app.property.guest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.ParentEntity;
import pl.app.property.property.model.PropertyEntity;

import java.time.Instant;
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
@Table(name = "t_guest")
public class GuestEntity extends AbstractEntity<UUID> implements
        ParentEntity<PropertyEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guest_id", nullable = false)
    private UUID guestId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String phone;
    @Column(name = "cell_phone")
    private String cellPhone;
    @Column(name = "date_of_birth")
    private Instant dateOfBirth;
    private String gender;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private GuestAddressEntity address;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    @Override
    public UUID getId() {
        return guestId;
    }

    @Override
    public PropertyEntity getParent() {
        return property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GuestEntity that = (GuestEntity) o;
        return guestId != null && Objects.equals(guestId, that.guestId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
