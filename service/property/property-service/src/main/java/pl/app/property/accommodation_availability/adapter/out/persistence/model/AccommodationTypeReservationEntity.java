package pl.app.property.accommodation_availability.adapter.out.persistence.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationAssignedStatus;

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
@Table(name = "t_accommodation_type_reservation")
public class AccommodationTypeReservationEntity extends AbstractEntity<UUID> implements
        RootAware<AccommodationTypeAvailabilityEntity> {
    @Id
    @Column(name = "accommodation_type_reservation_id", nullable = false)
    private UUID accommodationTypeReservationId;

    @Column(name = "accommodation_type_id", nullable = false)
    private UUID accommodationTypeId;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private AccommodationAssignedStatus status;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "accommodationTypeReservationEntity",
            cascade = CascadeType.ALL)
    @Builder.Default
    private Set<AccommodationReservationEntity> accommodationReservationEntities = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "accommodation_type_availability_id")
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypeAvailabilityEntity accommodationTypeAvailabilityEntity;

    @Override
    public UUID getId() {
        return accommodationTypeReservationId;
    }

    @Override
    public AccommodationTypeAvailabilityEntity root() {
        return accommodationTypeAvailabilityEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypeReservationEntity that = (AccommodationTypeReservationEntity) o;
        return accommodationTypeReservationId != null && Objects.equals(accommodationTypeReservationId, that.accommodationTypeReservationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
