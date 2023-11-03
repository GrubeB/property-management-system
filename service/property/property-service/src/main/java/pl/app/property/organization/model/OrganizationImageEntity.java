package pl.app.property.organization.model;

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
@Table(name = "t_organization_image")
public class OrganizationImageEntity extends AbstractEntity<UUID> implements RootAware<OrganizationEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "image_id", nullable = false)
    private UUID imageId;
    @Column(name = "file_id", nullable = false)
    private String fileId;
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    @ToString.Exclude
    @JsonIgnore
    private OrganizationEntity organization;

    @Override
    public UUID getId() {
        return imageId;
    }

    @Override
    public OrganizationEntity root() {
        return organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrganizationImageEntity that = (OrganizationImageEntity) o;
        return imageId != null && Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
