package pl.app.property.accommodation_availability.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.ParentEntity;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;

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
@Table(name = "t_accommodation_type_availability")
public class AccommodationTypeAvailabilityEntity extends AbstractEntity<UUID> implements
        ParentEntity<AccommodationTypeEntity> {
    @Id
    @Column(name = "accommodation_type_availability_id", nullable = false)
    private UUID accommodationTypeAvailabilityId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accommodation_type")
    private AccommodationTypeEntity accommodationType;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "accommodationTypeAvailabilityEntity",
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationReservationEntity> accommodationReservation = new LinkedHashSet<>();


    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "accommodationTypeAvailabilityEntity",
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationTypeReservationEntity> accommodationTypeReservation = new LinkedHashSet<>();

    @Override
    public UUID getId() {
        return accommodationTypeAvailabilityId;
    }

    @Override
    public AccommodationTypeEntity getParent() {
        return accommodationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypeAvailabilityEntity that = (AccommodationTypeAvailabilityEntity) o;
        return accommodationTypeAvailabilityId != null && Objects.equals(accommodationTypeAvailabilityId, that.accommodationTypeAvailabilityId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
