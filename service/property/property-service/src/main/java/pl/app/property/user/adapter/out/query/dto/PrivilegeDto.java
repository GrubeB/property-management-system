package pl.app.property.user.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeDto {
    private UUID privilegeId;
    private String domainObjectId;
    private PermissionDto permission;
}
