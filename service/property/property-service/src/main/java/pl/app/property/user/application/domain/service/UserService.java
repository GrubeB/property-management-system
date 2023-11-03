package pl.app.property.user.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.app.common.shared.authorization.PermissionLevel;
import pl.app.common.shared.authorization.PermissionName;
import pl.app.property.user.application.domain.model.Permission;
import pl.app.property.user.application.domain.model.User;
import pl.app.property.user.application.domain.model.UserOrganization;
import pl.app.property.user.application.port.in.*;
import pl.app.property.user.application.port.out.LoadUserPort;
import pl.app.property.user.application.port.out.SaveUserPort;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements
        AddPrivilegeUseCase,
        RemovePrivilegeUseCase,
        FetchUserUseCase,
        CreateUserUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID createUser(CreateUserCommand command) {
        String encodedPassword = passwordEncoder.encode(command.getPassword());
        User user = new User(command.getEmail(), encodedPassword);
        this.addOrganizationToUser(user, command.getOrganizationId());
        command.getPrivileges().forEach(p -> {
            this.addPrivilege(user, p.getPermissionName(), p.getPermissionLevel(), p.getDomainObjectId());
        });
        return saveUserPort.saveUser(user);
    }

    @Override
    public User fetchUser(FetchUserCommand command) {
        return loadUserPort.loadUserByEmail(command.getEmail());
    }

    @Override
    public void addPrivilege(AddPrivilegeCommand command) {
        User user = loadUserPort.loadUser(command.getUserId());
        command.getPrivileges().forEach(p ->
                this.addPrivilege(user, p.getPermissionName(), p.getPermissionLevel(), p.getDomainObjectId())
        );
        saveUserPort.saveUser(user);
    }

    @Override
    public void removePrivilege(RemovePrivilegeCommand command) {
        User user = loadUserPort.loadUser(command.getUserId());
        command.getPrivileges().forEach(p ->
                this.removePrivilege(user, p.getPermissionName(), p.getPermissionLevel(), p.getDomainObjectId())
        );
        saveUserPort.saveUser(user);
    }

    private void addPrivilege(User user, String permissionName, String permissionLevel, UUID domainObjectId) {
        Permission permission = new Permission(PermissionName.valueOf(permissionName), PermissionLevel.valueOf(permissionLevel));
        user.addPrivilege(permission, domainObjectId);
    }

    private void removePrivilege(User user, String permissionName, String permissionLevel, UUID domainObjectId) {
        Permission permission = new Permission(PermissionName.valueOf(permissionName), PermissionLevel.valueOf(permissionLevel));
        user.removePrivilege(permission, domainObjectId);
    }

    private void addOrganizationToUser(User user, UUID organizationId) {
        if (Objects.nonNull(organizationId)) {
            UserOrganization organization = new UserOrganization(organizationId);
            user.addOrganization(organization);
        }
    }
}
