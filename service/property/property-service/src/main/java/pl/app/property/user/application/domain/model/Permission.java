package pl.app.property.user.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.common.shared.authorization.PermissionLevel;
import pl.app.common.shared.authorization.PermissionName;

@Getter
@AllArgsConstructor
public class Permission {
    private PermissionName name;
    private PermissionLevel permissionLevel;
}
