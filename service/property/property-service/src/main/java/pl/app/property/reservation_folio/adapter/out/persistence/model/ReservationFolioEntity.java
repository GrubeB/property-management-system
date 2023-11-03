package pl.app.property.reservation_folio.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationEntity;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicyType;

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
@Table(name = "t_reservation_folio")
public class ReservationFolioEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "reservation_folio_id", nullable = false)
    private UUID reservationFolioId;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    @ToString.Exclude
    @JsonIgnore
    private ReservationEntity reservation;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ReservationPaymentPolicyType type;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "reservationFolio", orphanRemoval = true)
    @Builder.Default
    private Set<ReservationFolioPaymentEntity> payments = new LinkedHashSet<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "reservationFolio", orphanRemoval = true)
    @Builder.Default
    private Set<ReservationFolioChargeEntity> charges = new LinkedHashSet<>();

    @Override
    public UUID getId() {
        return reservationFolioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReservationFolioEntity that = (ReservationFolioEntity) o;
        return reservationFolioId != null && Objects.equals(reservationFolioId, that.reservationFolioId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
