package pl.app.property.registration.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.registration.application.domain.model.RegistrationSource;
import pl.app.property.registration.application.domain.model.RegistrationStatus;
import pl.app.property.registration_folio.adapter.out.persistence.model.RegistrationFolioEntity;

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
@Table(name = "t_registration")
public class RegistrationEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "registration_id", nullable = false)
    private UUID registrationId;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RegistrationSource source;

    @OneToOne(mappedBy = "registration", orphanRemoval = true)
    private RegistrationFolioEntity registrationFolio;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<RegistrationPartyEntity> parties = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<RegistrationBookingEntity> bookings = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    @Override
    public UUID getId() {
        return registrationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RegistrationEntity that = (RegistrationEntity) o;
        return registrationId != null && Objects.equals(registrationId, that.registrationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
