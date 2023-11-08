package pl.app.property.reservation.adapter.out.persistence.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;

import java.time.LocalDate;
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
@Table(name = "t_reservation_booking")
public class ReservationBookingEntity extends AbstractEntity<UUID> implements
        RootAware<ReservationEntity> {
    @Id
    @Column(name = "reservation_booking_id", nullable = false)
    private UUID reservationBookingId;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "accommodation_type_id", nullable = false)
    private UUID accommodationTypeId;
    @Column(name = "number_of_adults", nullable = false)
    private Integer numberOfAdults;
    @Column(name = "number_of_children", nullable = false)
    private Integer numberOfChildren;
    @Column(name = "accommodation_type_reservation_type_id")
    private UUID accommodationTypeReservationTypeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id", updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private ReservationEntity reservation;

    @Override
    public UUID getId() {
        return reservationBookingId;
    }

    @Override
    public ReservationEntity root() {
        return reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReservationBookingEntity that = (ReservationBookingEntity) o;
        return reservationBookingId != null && Objects.equals(reservationBookingId, that.reservationBookingId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
