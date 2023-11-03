package pl.app.property.accommodation.adapter.out.persistence.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.ParentEntity;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation")
public class AccommodationEntity extends AbstractEntity<UUID> implements
        ParentEntity<AccommodationTypeEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "accommodation_id", nullable = false)
    private UUID accommodationId;
    @Column(name = "accommodation_name", nullable = false)
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accommodation_type", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypeEntity accommodationType;

    @Override
    public UUID getId() {
        return accommodationId;
    }

    @Override
    public AccommodationTypeEntity getParent() {
        return accommodationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationEntity that = (AccommodationEntity) o;
        return accommodationId != null && Objects.equals(accommodationId, that.accommodationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
