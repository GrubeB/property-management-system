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
@Table(name = "t_accommodation_image")
public class AccommodationTypeImageEntity extends AbstractEntity<UUID> implements
        RootAware<AccommodationTypeEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "image_id", nullable = false)
    private UUID imageId;
    @Column(name = "file_id", nullable = false)
    private String fileId;
    private String description;

    @ManyToOne
    @JoinColumn(name = "accommodation_type_id")
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypeEntity accommodationType;

    @Override
    public UUID getId() {
        return imageId;
    }

    @Override
    public AccommodationTypeEntity root() {
        return accommodationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypeImageEntity that = (AccommodationTypeImageEntity) o;
        return imageId != null && Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
