package pl.app.property.registration.adapter.out.persistence.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;
import pl.app.property.guest.model.GuestEntity;

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
@Table(name = "t_registration_party")
public class RegistrationPartyEntity extends AbstractEntity<UUID> implements RootAware<RegistrationEntity> {
    @Id
    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "t_registration_party_guests",
            joinColumns = @JoinColumn(name = "party_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
    @Builder.Default
    private Set<GuestEntity> guests = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private RegistrationEntity registration;

    @Override
    public UUID getId() {
        return partyId;
    }

    @Override
    public RegistrationEntity root() {
        return registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RegistrationPartyEntity that = (RegistrationPartyEntity) o;
        return partyId != null && Objects.equals(partyId, that.partyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
