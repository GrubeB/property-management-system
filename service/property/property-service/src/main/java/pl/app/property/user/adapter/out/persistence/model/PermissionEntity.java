package pl.app.property.user.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.shared.authorization.PermissionDomainObjectType;
import pl.app.common.shared.authorization.PermissionName;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_permission", uniqueConstraints = {
        @UniqueConstraint(name = "uc_t_permission_permission_name_and_permission_level", columnNames = {"permission_name", "permission_domain_object_type"})
})
public class PermissionEntity extends AbstractEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "permission_id", nullable = false)
    private UUID permissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_domain_object_type", nullable = false)
    private PermissionDomainObjectType permissionDomainObjectType;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_name", nullable = false)
    private PermissionName permissionName;

    @Override
    public UUID getId() {
        return permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PermissionEntity that = (PermissionEntity) o;
        return permissionId != null && Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
