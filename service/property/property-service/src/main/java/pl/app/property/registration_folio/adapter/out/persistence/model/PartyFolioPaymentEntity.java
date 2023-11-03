package pl.app.property.registration_folio.adapter.out.persistence.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;

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
@Table(name = "t_registration_party_folio_payment")
public class PartyFolioPaymentEntity extends AbstractEntity<UUID> implements RootAware<RegistrationFolioEntity> {
    @Id
    @Column(name = "payment_id", nullable = false)
    private UUID paymentId;
    @Column(name = "guest_id", nullable = false)
    private UUID guestId;
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
        return paymentId;
    }

    @Override
    public RegistrationFolioEntity root() {
        return partyFolio.root();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PartyFolioPaymentEntity that = (PartyFolioPaymentEntity) o;
        return getPaymentId() != null && Objects.equals(getPaymentId(), that.getPaymentId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
