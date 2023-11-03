package pl.app.property.registration_folio.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;

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
@Table(name = "t_registration_party_folio")
public class PartyFolioEntity extends AbstractEntity<UUID> implements RootAware<RegistrationFolioEntity> {
    @Id
    @Column(name = "party_folio_id", nullable = false)
    private UUID partyFolioId;
    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "partyFolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private Set<PartyFolioPaymentEntity> payments = new LinkedHashSet<>();
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "partyFolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private Set<PartyFolioChargeEntity> charges = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "registration_folio_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private RegistrationFolioEntity registrationFolio;

    @Override
    public UUID getId() {
        return partyFolioId;
    }

    @Override
    public RegistrationFolioEntity root() {
        return registrationFolio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PartyFolioEntity that = (PartyFolioEntity) o;
        return partyFolioId != null && Objects.equals(partyFolioId, that.partyFolioId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
