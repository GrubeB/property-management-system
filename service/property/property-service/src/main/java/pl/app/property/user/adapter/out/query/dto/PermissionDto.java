package pl.app.property.user.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.shared.authorization.PermissionDomainObjectType;
import pl.app.common.shared.authorization.PermissionName;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private PermissionName permissionName;
    private PermissionDomainObjectType permissionDomainObjectType;
}
