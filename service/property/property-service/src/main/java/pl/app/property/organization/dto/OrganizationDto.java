package pl.app.property.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.organization.model.OrganizationImageEntity;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDto implements Serializable {
    private UUID organizationId;
    private String name;
    private OrganizationImageEntity logo;
    private Set<OrganizationImageDto> organizationImages;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrganizationImageDto implements Serializable {
        private UUID imageId;
        private String fileId;
        private String description;
    }
}
