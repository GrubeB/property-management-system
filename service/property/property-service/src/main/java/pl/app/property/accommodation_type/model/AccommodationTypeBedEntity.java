package pl.app.property.accommodation_type.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type_bed")
public class AccommodationTypeBedEntity extends AbstractEntity<UUID> implements
        RootAware<AccommodationTypeEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bed_id", nullable = false)
    private UUID bedId;
    @Column(name = "number_of_beds", nullable = false)
    private Integer numberOfBeds;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BedType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_type_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypeEntity accommodationType;

    @Override
    public UUID getId() {
        return bedId;
    }

    @Override
    public AccommodationTypeEntity root() {
        return accommodationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypeBedEntity that = (AccommodationTypeBedEntity) o;
        return getBedId() != null && Objects.equals(getBedId(), that.getBedId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
