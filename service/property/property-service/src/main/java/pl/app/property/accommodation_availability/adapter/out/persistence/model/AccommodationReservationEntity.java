package pl.app.property.accommodation_availability.adapter.out.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;
import pl.app.property.accommodation.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationStatus;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_reservation")
public class AccommodationReservationEntity extends AbstractEntity<UUID> implements RootAware<AccommodationTypeAvailabilityEntity> {
    @Id
    @Column(name = "accommodation_reservation_id", nullable = false)
    private UUID accommodationReservationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accommodation_id")
    private AccommodationEntity accommodation;
    @Enumerated(value = EnumType.STRING)
    private AccommodationStatus status;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "accommodation_type_reservation_id")
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypeReservationEntity accommodationTypeReservationEntity;

    @ManyToOne
    @JoinColumn(name = "accommodation_type_availability_id")
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypeAvailabilityEntity accommodationTypeAvailabilityEntity;

    @Override
    public UUID getId() {
        return accommodationReservationId;
    }

    @Override
    public AccommodationTypeAvailabilityEntity root() {
        return accommodationTypeAvailabilityEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationReservationEntity that = (AccommodationReservationEntity) o;
        return accommodationReservationId != null && Objects.equals(accommodationReservationId, that.accommodationReservationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
