package pl.app.property.user.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.property.organization.model.OrganizationEntity;
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
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(name = "uc_t_user_email", columnNames = {"email"})
})
public class UserEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "user",
            orphanRemoval = true)
    @Builder.Default
    private Set<PrivilegeEntity> privileges = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_organization",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id"))
    @Builder.Default
    private Set<OrganizationEntity> organizations = new LinkedHashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_property",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id"))
    @Builder.Default
    private Set<PropertyEntity> properties = new LinkedHashSet<>();

    @Override
    public UUID getId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
