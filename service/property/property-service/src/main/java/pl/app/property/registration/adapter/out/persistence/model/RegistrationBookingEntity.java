package pl.app.property.registration.adapter.out.persistence.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;
import pl.app.property.guest.model.GuestEntity;

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
@Table(name = "t_registration_booking")
public class RegistrationBookingEntity extends AbstractEntity<UUID> implements RootAware<RegistrationEntity> {
    @Id
    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "accommodation_type_id", nullable = false)
    private UUID accommodationTypeId;
    @Column(name = "accommodation_type_reservation_id")
    private UUID accommodationTypeReservationId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "t_registration_booking_guests",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
    @Builder.Default
    private Set<GuestEntity> guests = new LinkedHashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "charge_id", nullable = false)
    @CollectionTable(name = "t_registration_booking_charge_ids", joinColumns = @JoinColumn(name = "booking_id"))
    @Builder.Default
    private Set<UUID> chargeIds = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private RegistrationEntity registration;

    @Override
    public UUID getId() {
        return bookingId;
    }

    @Override
    public RegistrationEntity root() {
        return registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RegistrationBookingEntity that = (RegistrationBookingEntity) o;
        return bookingId != null && Objects.equals(bookingId, that.bookingId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
