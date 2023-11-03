package pl.app.property.user.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID userId;
    private String email;
    private Set<PrivilegeDto> privileges;
    private Set<Organization> organizations;
    private Set<Property> properties;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Organization {
        private UUID organizationId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Property {
        private UUID propertyId;
    }
}
