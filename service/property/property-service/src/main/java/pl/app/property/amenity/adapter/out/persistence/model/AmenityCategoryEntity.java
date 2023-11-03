package pl.app.property.amenity.adapter.out.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_amenity_category")
public class AmenityCategoryEntity extends AbstractEntity<UUID> {

    @Id
    @Column(name = "category_id", nullable = false)
    private UUID categoryId;
    @Column(name = "category_name", nullable = false)
    private String name;

    @Override
    public UUID getId() {
        return categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AmenityCategoryEntity that = (AmenityCategoryEntity) o;
        return name != null && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
