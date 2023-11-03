package pl.app.property.user.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

    public void addOrganization(UserOrganization organization) {
        this.organizations.add(organization);
    }

    public void addProperty(UserProperty property) {
        this.properties.add(property);
    }

    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
    }

    public void addPrivilege(Permission permission, UUID domainObjectId) {
        Privilege newPrivilege = new Privilege(permission, domainObjectId);
        this.addPrivilege(newPrivilege);
    }

    public void removePrivilege(Permission permission, UUID domainObjectId) {
        this.privileges.removeIf(p ->
                Objects.equals(p.getDomainObjectId(), domainObjectId)
                        && Objects.equals(p.getPermission().getName(), permission.getName())
                        && Objects.equals(p.getPermission().getPermissionLevel(), permission.getPermissionLevel())
        );
    }
}
