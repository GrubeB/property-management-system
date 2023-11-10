package pl.app.property.user.adapter.out.persistence.model;

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
@Table(name = "t_privilege")
public class PrivilegeEntity extends AbstractEntity<UUID> implements RootAware<UserEntity> {
    @Id
    @Column(name = "privilege_id", nullable = false)
    private UUID privilegeId;

    @Column(name = "domain_object_id")
    private UUID domainObjectId;

    @OneToOne(optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permission;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private UserEntity user;

    @Override
    public UUID getId() {
        return privilegeId;
    }

    @Override
    public UserEntity root() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PrivilegeEntity that = (PrivilegeEntity) o;
        return privilegeId != null && Objects.equals(privilegeId, that.privilegeId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
