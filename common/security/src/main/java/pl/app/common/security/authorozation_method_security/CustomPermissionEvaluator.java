package pl.app.common.security.authorozation_method_security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import pl.app.common.shared.authorization.PermissionDomainObjectType;
import pl.app.common.shared.authorization.PermissionName;

import java.io.Serializable;


class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String permissionLevel, Object permissionName) {
        if ((authentication == null) || (permissionLevel == null) || !(permissionName instanceof PermissionName)) {
            return false;
        }
        if (targetId == null) {
            return hasPrivilege(authentication, PermissionDomainObjectType.valueOf(permissionLevel), (PermissionName) permissionName);
        }
        return hasPrivilege(authentication, PermissionDomainObjectType.valueOf(permissionLevel), (PermissionName) permissionName, (String) targetId);
    }

    private boolean hasPrivilege(Authentication authentication, PermissionDomainObjectType permissionDomainObjectType, PermissionName permissionName, String targetId) {
        return authentication.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority instanceof DomainObjectGrandAuthority domainObjectGrandAuthority &&
                        domainObjectGrandAuthority.getPermissionName().equals(permissionName.name()) &&
                        domainObjectGrandAuthority.getDomainObjectId().equals(targetId) &&
                        domainObjectGrandAuthority.getDomainObjectType().equals(permissionDomainObjectType.name()));
    }

    private boolean hasPrivilege(Authentication authentication, PermissionDomainObjectType permissionDomainObjectType, PermissionName permissionName) {
        return authentication.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority instanceof DomainObjectGrandAuthority domainObjectGrandAuthority &&
                        domainObjectGrandAuthority.getPermissionName().equals(permissionName.name()) &&
                        (domainObjectGrandAuthority.getDomainObjectId() == null ||
                                domainObjectGrandAuthority.getDomainObjectId().isBlank()) &&
                        domainObjectGrandAuthority.getDomainObjectType().equals(permissionDomainObjectType.name()));
    }
}
