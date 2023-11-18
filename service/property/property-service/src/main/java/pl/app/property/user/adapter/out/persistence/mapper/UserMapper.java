package pl.app.property.user.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.organization.service.OrganizationQueryService;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;
import pl.app.property.user.adapter.out.persistence.model.PermissionEntity;
import pl.app.property.user.adapter.out.persistence.model.PrivilegeEntity;
import pl.app.property.user.adapter.out.persistence.model.UserEntity;
import pl.app.property.user.adapter.out.persistence.repository.PermissionRepository;
import pl.app.property.user.application.domain.exception.UserException;
import pl.app.property.user.application.domain.model.*;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final OrganizationQueryService organizationQueryService;
    private final PropertyQueryService propertyQueryService;
    private final PermissionRepository permissionRepository;

    public UserEntity mapToUserEntity(User domain) {
        UserEntity entity = UserEntity.builder()
                .userId(domain.getUserId())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .privileges(domain.getPrivileges().stream().map(this::mapToPrivilegeEntity).collect(Collectors.toSet()))
                .organizations(domain.getOrganizations().stream().map(this::mapToOrganizationEntity).collect(Collectors.toSet()))
                .properties(domain.getProperties().stream().map(this::mapToPropertyEntity).collect(Collectors.toSet()))
                .build();
        entity.getPrivileges().forEach(p -> p.setUser(entity));
        return entity;
    }

    public PropertyEntity mapToPropertyEntity(UserProperty domain) {
        return propertyQueryService.fetchById(domain.getPropertyId());
    }

    public OrganizationEntity mapToOrganizationEntity(UserOrganization domain) {
        return organizationQueryService.fetchById(domain.getOrganizationId());
    }

    public PrivilegeEntity mapToPrivilegeEntity(Privilege domain) {
        return PrivilegeEntity.builder()
                .privilegeId(domain.getPrivilegeId())
                .domainObjectId(domain.getDomainObjectId())
                .permission(this.mapToPermissionEntity(domain.getPermission()))
                .build();
    }

    public PermissionEntity mapToPermissionEntity(Permission domain) {
        return permissionRepository.findByPermissionNameAndPermissionName(domain.getName(), domain.getPermissionDomainObjectType())
                .orElseThrow(() -> UserException.NotFoundPermissionException.fromName(domain.getName().toString()));
    }


    public User mapToUser(UserEntity entity) {
        return new User(
                entity.getUserId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPrivileges().stream().map(this::mapToPrivilege).collect(Collectors.toList()),
                entity.getOrganizations().stream().map(this::mapToUserOrganization).collect(Collectors.toList()),
                entity.getProperties().stream().map(this::mapToUserProperty).collect(Collectors.toList())
        );
    }

    public UserProperty mapToUserProperty(PropertyEntity entity) {
        return new UserProperty(entity.getPropertyId());
    }

    public UserOrganization mapToUserOrganization(OrganizationEntity entity) {
        return new UserOrganization(entity.getOrganizationId());
    }

    public Privilege mapToPrivilege(PrivilegeEntity entity) {
        return new Privilege(
                entity.getPrivilegeId(),
                this.mapToPermission(entity.getPermission()),
                entity.getDomainObjectId()
        );
    }

    public Permission mapToPermission(PermissionEntity entity) {
        return new Permission(
                entity.getPermissionName(),
                entity.getPermissionDomainObjectType()
        );
    }
}
