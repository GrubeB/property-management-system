package pl.app.common.security.authorization;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import pl.app.common.shared.authorization.PermissionDomainObjectType;
import pl.app.common.shared.authorization.PermissionName;

import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final PermissionName permissionName;
    private final PermissionDomainObjectType permissionDomainObjectType;
    private final String targetIdVariableName;

    private final List<CustomAuthorizationManager> children;
    private final GroupAuthorizationManagerType type;

    private final AuthorizationService authorizationService;

    enum GroupAuthorizationManagerType {
        ALL,
        ONE,
    }

    CustomAuthorizationManager(PermissionDomainObjectType permissionDomainObjectType, PermissionName permissionName, String targetIdVariableName) {
        this.permissionDomainObjectType = permissionDomainObjectType;
        this.permissionName = permissionName;
        this.targetIdVariableName = targetIdVariableName;
        this.children = null;
        this.type = null;
        this.authorizationService = AuthorizationServiceProvider.getAuthorizationInstance();
    }

    CustomAuthorizationManager(GroupAuthorizationManagerType type, CustomAuthorizationManager... children) {
        this.permissionDomainObjectType = null;
        this.permissionName = null;
        this.targetIdVariableName = null;
        this.children = Stream.of(children).collect(Collectors.toList());
        this.type = type;
        this.authorizationService = AuthorizationServiceProvider.getAuthorizationInstance();
    }

    public static CustomAuthorizationManager hasRootPermission(PermissionName permissionName) {
        return new CustomAuthorizationManager(PermissionDomainObjectType.ROOT, permissionName, null);
    }

    public static CustomAuthorizationManager hasOrganizationPermission(PermissionName permissionName) {
        return new CustomAuthorizationManager(PermissionDomainObjectType.ORGANIZATION, permissionName, "organizationId");
    }

    public static CustomAuthorizationManager hasPropertyPermission(PermissionName permissionName) {
        return new CustomAuthorizationManager(PermissionDomainObjectType.PROPERTY, permissionName, "propertyId");
    }

    public static CustomAuthorizationManager anyOf(CustomAuthorizationManager... all) {
        return new CustomAuthorizationManager(GroupAuthorizationManagerType.ONE, all);
    }

    public static CustomAuthorizationManager allOf(CustomAuthorizationManager... all) {
        return new CustomAuthorizationManager(GroupAuthorizationManagerType.ALL, all);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        if (children != null) {
            return new AuthorizationDecision(checkGroup(authentication, context));
        } else {
            return new AuthorizationDecision(checkOne(authentication, context));
        }
    }

    private boolean checkGroup(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        return switch (this.type){
            case ALL -> this.children.stream().allMatch(child -> child.checkOne(authentication, context));
            case ONE -> this.children.stream().anyMatch(child -> child.checkOne(authentication, context));
        };
    }

    private boolean checkOne(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Serializable targetId = getTargetId(context);
        if (targetId == null) {
            return authorizationService.hasPrivilege(authentication.get(), this.permissionDomainObjectType.name(), this.permissionName.name());
        } else {
            return authorizationService.hasPrivilege(authentication.get(), targetId, this.permissionDomainObjectType.name(), this.permissionName.name());
        }
    }

    private String getTargetId(RequestAuthorizationContext context) {
        return context.getVariables().get(this.targetIdVariableName);
    }
}
