package pl.app.property.organization.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.property.property.model.PropertyEntity;

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
@Table(name = "t_organization")
public class OrganizationEntity extends AbstractEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;
    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "logo_id")
    private OrganizationImageEntity logo;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "organization",
            orphanRemoval = true)
    @Builder.Default
    private Set<OrganizationImageEntity> organizationImages = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "organization",
            orphanRemoval = true)
    @Builder.Default
    private Set<PropertyEntity> properties = new LinkedHashSet<>();

    public void setOrganizationImages(Set<OrganizationImageEntity> organizationImages) {
        this.organizationImages.clear();
        organizationImages.stream()
                .peek(ch -> ch.setOrganization(this))
                .forEach(this.organizationImages::add);
    }

    @Override
    public UUID getId() {
        return this.organizationId;
    }

    @Override
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrganizationEntity that = (OrganizationEntity) o;
        return organizationId != null && Objects.equals(organizationId, that.organizationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
