package pl.app.common.security.authorization;

import org.springframework.security.core.Authentication;

import java.io.Serializable;

public interface AuthorizationService {

    boolean hasPrivilege(String permissionLevel, String permissionName);

    boolean hasPrivilege(Serializable targetId, String permissionLevel, String permissionName);

    boolean hasPrivilege(Authentication authentication, String permissionLevel, String permissionName);

    boolean hasPrivilege(Authentication authentication, Serializable targetId, String permissionLevel, String permissionName);

    boolean hasRootPrivilege(String permissionName);

    boolean hasRootPrivilege(Authentication authentication, String permissionName);

    boolean hasOrganizationPrivilege(Serializable targetId, String permissionName);

    boolean hasOrganizationPrivilege(Authentication authentication, Serializable targetId, String permissionName);

    boolean hasPropertyPrivilege(Serializable targetId, String permissionName);

    boolean hasPropertyPrivilege(Authentication authentication, Serializable targetId, String permissionName);
}
