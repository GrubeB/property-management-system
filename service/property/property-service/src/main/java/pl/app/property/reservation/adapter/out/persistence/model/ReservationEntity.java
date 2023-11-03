package pl.app.property.reservation.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.ParentEntity;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.reservation.application.domain.model.ReservationSource;
import pl.app.property.reservation.application.domain.model.ReservationStatus;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioEntity;

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
@Table(name = "t_reservation")
public class ReservationEntity extends AbstractEntity<UUID> implements
        ParentEntity<PropertyEntity> {
    @Id
    @Column(name = "reservation_id", nullable = false)
    private UUID reservationId;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;
    @Enumerated(value = EnumType.STRING)
    private ReservationSource source;

    @OneToOne(mappedBy = "reservation", orphanRemoval = true)
    private ReservationFolioEntity reservationFolio;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "main_guest_id", nullable = false)
    private GuestEntity mainGuest;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "t_reservation_guest",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
    @Builder.Default
    private Set<GuestEntity> guests = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "reservation", orphanRemoval = true)
    @Builder.Default
    private Set<ReservationBookingEntity> accommodationTypeBookings = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    @Override
    public UUID getId() {
        return reservationId;
    }

    @Override
    public PropertyEntity getParent() {
        return property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReservationEntity entity = (ReservationEntity) o;
        return reservationId != null && Objects.equals(reservationId, entity.reservationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
