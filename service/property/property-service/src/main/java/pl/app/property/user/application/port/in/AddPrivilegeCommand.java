package pl.app.property.user.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddPrivilegeCommand implements Serializable {
    private UUID userId;
    private List<Privilege> privileges;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Privilege {
        private UUID domainObjectId;
        private String permissionObjectType;
        private String permissionName;
    }
}