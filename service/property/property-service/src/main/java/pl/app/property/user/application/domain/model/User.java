package pl.app.property.user.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.common.shared.authorization.PermissionDomainObjectType;
import pl.app.common.shared.authorization.PermissionName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {
    private UUID userId;

    private String email;
    private String password;

    private List<Privilege> privileges;
    private List<UserOrganization> organizations;
    private List<UserProperty> properties;

    public User(String email, String password) {
        this.userId = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.privileges = new ArrayList<>();
        this.organizations = new ArrayList<>();
        this.properties = new ArrayList<>();
    }

    // ORGANIZATION

    public void addOrganization(UUID organizationId) {
        if (Objects.nonNull(organizationId) && !userIsInOrganization(organizationId)) {
            this.organizations.add(new UserOrganization(organizationId));
        }
    }

    public void removeOrganization(UUID organizationId) {
        if (Objects.nonNull(organizationId) && userIsInOrganization(organizationId)) {
            this.organizations.removeIf(organization -> Objects.equals(organization.getOrganizationId(), organizationId));
        }
    }

    public boolean userIsInOrganization(UUID organizationId) {
        return this.organizations.stream().anyMatch(organization -> Objects.equals(organization.getOrganizationId(), organizationId));
    }

    // PROPERTY

    public void addProperty(UUID propertyId) {
        if (Objects.nonNull(propertyId) && !userIsInProperty(propertyId)) {
            this.properties.add(new UserProperty(propertyId));
        }
    }

    public void removeProperty(UUID propertyId) {
        if (Objects.nonNull(propertyId) && userIsInProperty(propertyId)) {
            this.properties.removeIf(property -> Objects.equals(property.getPropertyId(), propertyId));
        }
    }

    public boolean userIsInProperty(UUID propertyId) {
        return this.properties.stream().anyMatch(property -> Objects.equals(property.getPropertyId(), propertyId));
    }

    // PRIVILEGE
    public void addPrivilege(Privilege privilege) {
        if (Objects.nonNull(privilege) && Objects.nonNull(privilege.getPermission()) && !userHasPrivilege(privilege)) {
            this.privileges.add(privilege);
        }
    }

    public void addPrivilege(Permission permission, UUID domainObjectId) {
        Privilege newPrivilege = new Privilege(permission, domainObjectId);
        addPrivilege(newPrivilege);
    }

    public void addPrivilege(String permissionName, String permissionLevel, UUID domainObjectId) {
        Permission permission = new Permission(PermissionName.valueOf(permissionName), PermissionDomainObjectType.valueOf(permissionLevel));
        addPrivilege(permission, domainObjectId);
    }

    public void removePrivilege(String permissionName, String permissionLevel, UUID domainObjectId) {
        Permission permission = new Permission(PermissionName.valueOf(permissionName), PermissionDomainObjectType.valueOf(permissionLevel));
        removePrivilege(permission, domainObjectId);
    }

    public void removePrivilege(Permission permission, UUID domainObjectId) {
        this.privileges.removeIf(privilege ->
                Objects.equals(privilege.getDomainObjectId(), domainObjectId)
                        && Objects.equals(privilege.getPermission().getName(), permission.getName())
                        && Objects.equals(privilege.getPermission().getPermissionDomainObjectType(), permission.getPermissionDomainObjectType())
        );
    }

    public boolean userHasPrivilege(Privilege privilege) {
        return userHasPrivilege(privilege.getPermission(), privilege.getDomainObjectId());
    }

    public boolean userHasPrivilege(Permission permission, UUID domainObjectId) {
        return this.privileges.stream()
                .anyMatch(privilege -> Objects.equals(privilege.getDomainObjectId(), domainObjectId)
                        && Objects.equals(privilege.getPermission().getName(), permission.getName())
                        && Objects.equals(privilege.getPermission().getPermissionDomainObjectType(), permission.getPermissionDomainObjectType())
                );
    }
}
