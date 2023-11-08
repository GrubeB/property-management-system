package pl.app.property.reservation_folio.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolioChargeType;

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
@Table(name = "t_reservation_folio_charge")
public class ReservationFolioChargeEntity extends AbstractEntity<UUID> implements RootAware<ReservationFolioEntity> {
    @Id
    @Column(name = "charge_id", nullable = false)
    private UUID chargeId;
    @Column(name = "object_id")
    private UUID objectId;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReservationFolioChargeType type;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private String current;
    @Column(nullable = false)
    private Instant date;
    @Column(name = "should_by_paid", nullable = false)
    private Boolean shouldByPaidBeforeRegistration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_folio_id", updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private ReservationFolioEntity reservationFolio;

    @Override
    public UUID getId() {
        return chargeId;
    }

    @Override
    public ReservationFolioEntity root() {
        return reservationFolio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReservationFolioChargeEntity that = (ReservationFolioChargeEntity) o;
        return chargeId != null && Objects.equals(chargeId, that.chargeId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
