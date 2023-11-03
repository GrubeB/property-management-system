package pl.app.property.property.model;

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
@Table(name = "t_property_image")
public class PropertyImageEntity extends AbstractEntity<UUID> implements
        RootAware<PropertyEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "image_id", nullable = false)
    private UUID imageId;
    @Column(name = "file_id", nullable = false)
    private String fileId;
    private String description;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    @Override
    public UUID getId() {
        return imageId;
    }

    @Override
    public PropertyEntity root() {
        return property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyImageEntity that = (PropertyImageEntity) o;
        return imageId != null && Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
