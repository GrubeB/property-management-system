package pl.app.property.registration_folio.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioChargeType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_registration_party_folio_charge")
public class PartyFolioChargeEntity extends AbstractEntity<UUID> implements RootAware<RegistrationFolioEntity> {
    @Id
    @Column(name = "charge_id", nullable = false)
    private UUID chargeId;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RegistrationPartyFolioChargeType type;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private String current;
    @Column(nullable = false)
    private Instant date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "party_folio_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PartyFolioEntity partyFolio;

    @Override
    public UUID getId() {
        return chargeId;
    }

    @Override
    public RegistrationFolioEntity root() {
        return partyFolio.root();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PartyFolioChargeEntity that = (PartyFolioChargeEntity) o;
        return getChargeId() != null && Objects.equals(getChargeId(), that.getChargeId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
