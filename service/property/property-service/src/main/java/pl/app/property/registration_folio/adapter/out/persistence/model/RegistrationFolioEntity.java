package pl.app.property.registration_folio.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationEntity;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_registration_folio")
public class RegistrationFolioEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "registration_folio_id", nullable = false)
    private UUID registrationFolioId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "registrationFolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<PartyFolioEntity> partyFolios = new LinkedHashSet<>();

    @OneToOne
    @JoinColumn(name = "registration_id")
    @ToString.Exclude
    @JsonIgnore
    private RegistrationEntity registration;

    @Override
    public UUID getId() {
        return registrationFolioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RegistrationFolioEntity that = (RegistrationFolioEntity) o;
        return registrationFolioId != null && Objects.equals(registrationFolioId, that.registrationFolioId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
