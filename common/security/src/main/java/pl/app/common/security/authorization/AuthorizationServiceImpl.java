package pl.app.common.security.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import pl.app.common.security.authentication.AuthenticationService;
import pl.app.common.security.authorozation_method_security.DomainObjectGrandAuthority;
import pl.app.common.shared.authorization.PermissionDomainObjectType;

import java.io.Serializable;

@RequiredArgsConstructor
class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthenticationService authenticationService;

    @Override
    public boolean hasPrivilege(String permissionLevel, String permissionName) {
        Authentication currentAuthentication = authenticationService.getCurrentAuthentication();
        return hasPrivilege(currentAuthentication, permissionLevel, permissionName);
    }

    @Override
    public boolean hasPrivilege(Serializable targetId, String permissionLevel, String permissionName) {
        Authentication currentAuthentication = authenticationService.getCurrentAuthentication();
        return hasPrivilege(currentAuthentication, targetId, permissionLevel, permissionName);
    }

    @Override
    public boolean hasPrivilege(Authentication authentication, String permissionLevel, String permissionName) {
        if ((authentication == null) || (permissionLevel == null) || permissionName == null) {
            return false;
        }
        return authentication.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority instanceof DomainObjectGrandAuthority domainObjectGrandAuthority &&
                        domainObjectGrandAuthority.getPermissionName().equals(permissionName) &&
                        (domainObjectGrandAuthority.getDomainObjectId() == null ||
                                domainObjectGrandAuthority.getDomainObjectId().isBlank()) &&
                        domainObjectGrandAuthority.getDomainObjectType().equals(permissionLevel));
    }

    @Override
    public boolean hasPrivilege(Authentication authentication, Serializable targetId, String permissionLevel, String permissionName) {
        if ((authentication == null) || (permissionLevel == null) || permissionName == null) {
            return false;
        }
        if (targetId == null) {
            return hasPrivilege(authentication, permissionLevel, permissionName);
        }
        return authentication.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority instanceof DomainObjectGrandAuthority domainObjectGrandAuthority &&
                        domainObjectGrandAuthority.getPermissionName().equals(permissionName) &&
                        domainObjectGrandAuthority.getDomainObjectId().equals(targetId) &&
                        domainObjectGrandAuthority.getDomainObjectType().equals(permissionLevel));
    }

    @Override
    public boolean hasRootPrivilege(String permissionName) {
        Authentication currentAuthentication = authenticationService.getCurrentAuthentication();
        return hasPrivilege(currentAuthentication, PermissionDomainObjectType.ROOT.name(), permissionName);
    }

    @Override
    public boolean hasRootPrivilege(Authentication authentication, String permissionName) {
        return hasPrivilege(authentication, PermissionDomainObjectType.ROOT.name(), permissionName);
    }

    @Override
    public boolean hasOrganizationPrivilege(Serializable targetId, String permissionName) {
        Authentication currentAuthentication = authenticationService.getCurrentAuthentication();
        return hasPrivilege(currentAuthentication, targetId, PermissionDomainObjectType.ORGANIZATION.name(), permissionName);
    }

    @Override
    public boolean hasOrganizationPrivilege(Authentication authentication, Serializable targetId, String permissionName) {
        return hasPrivilege(authentication, targetId, PermissionDomainObjectType.ORGANIZATION.name(), permissionName);
    }

    @Override
    public boolean hasPropertyPrivilege(Serializable targetId, String permissionName) {
        Authentication currentAuthentication = authenticationService.getCurrentAuthentication();
        return hasPrivilege(currentAuthentication, targetId, PermissionDomainObjectType.PROPERTY.name(), permissionName);
    }

    @Override
    public boolean hasPropertyPrivilege(Authentication authentication, Serializable targetId, String permissionName) {
        return hasPrivilege(authentication, targetId, PermissionDomainObjectType.PROPERTY.name(), permissionName);
    }
}
