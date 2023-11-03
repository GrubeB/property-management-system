package pl.app.property.reservation_folio.adapter.out.persistence.model;


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
@Table(name = "t_reservation_folio_payment")
public class ReservationFolioPaymentEntity extends AbstractEntity<UUID> implements RootAware<ReservationFolioEntity> {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_folio_id", updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private ReservationFolioEntity reservationFolio;

    @Override
    public UUID getId() {
        return paymentId;
    }

    @Override
    public ReservationFolioEntity root() {
        return reservationFolio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReservationFolioPaymentEntity that = (ReservationFolioPaymentEntity) o;
        return paymentId != null && Objects.equals(paymentId, that.paymentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
