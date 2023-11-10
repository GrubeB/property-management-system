package pl.app.property.user.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.app.property.user.application.domain.model.User;
import pl.app.property.user.application.port.in.*;
import pl.app.property.user.application.port.out.LoadUserPort;
import pl.app.property.user.application.port.out.SaveUserPort;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements
        RemovePropertyFromUserUseCase,
        RemoveOrganizationFromUserUseCase,
        AddOrganizationToUserUseCase,
        AddPropertyToUserUseCase,
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
        user.addOrganization(command.getOrganizationId());
        command.getPrivileges().forEach(p -> user.addPrivilege(p.getPermissionName(), p.getPermissionLevel(), p.getDomainObjectId()));
        return saveUserPort.saveUser(user);
    }

    @Override
    public User fetchUser(FetchUserCommand command) {
        return loadUserPort.loadUserByEmail(command.getEmail());
    }

    @Override
    public void addPrivilege(AddPrivilegeCommand command) {
        User user = loadUserPort.loadUser(command.getUserId());
        command.getPrivileges().forEach(p -> user.addPrivilege(p.getPermissionName(), p.getPermissionLevel(), p.getDomainObjectId()));
        saveUserPort.saveUser(user);
    }

    @Override
    public void removePrivilege(RemovePrivilegeCommand command) {
        User user = loadUserPort.loadUser(command.getUserId());
        command.getPrivileges().forEach(p -> user.removePrivilege(p.getPermissionName(), p.getPermissionLevel(), p.getDomainObjectId()));
        saveUserPort.saveUser(user);
    }

    @Override
    public void addOrganizationToUser(AddOrganizationToUserCommand command) {
        User user = loadUserPort.loadUser(command.getUserId());
        user.addOrganization(command.getOrganizationId());
        saveUserPort.saveUser(user);
    }

    @Override
    public void addPropertyToUser(AddPropertyToUserCommand command) {
        User user = loadUserPort.loadUser(command.getUserId());
        user.addProperty(command.getPropertyId());
        saveUserPort.saveUser(user);
    }

    @Override
    public void removeOrganizationFromUser(RemoveOrganizationFromUserCommand command) {
        User user = loadUserPort.loadUser(command.getUserId());
        user.removeOrganization(command.getOrganizationId());
        saveUserPort.saveUser(user);
    }

    @Override
    public void removePropertyFromUser(RemovePropertyFromUserCommand command) {
        User user = loadUserPort.loadUser(command.getUserId());
        user.removeProperty(command.getPropertyId());
        saveUserPort.saveUser(user);
    }
}
