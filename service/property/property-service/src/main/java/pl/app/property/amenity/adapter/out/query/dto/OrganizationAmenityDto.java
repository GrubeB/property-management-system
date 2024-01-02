package pl.app.property.amenity.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationAmenityDto implements Serializable {
    private UUID organizationAmenityId;
    private UUID organizationId;
    private Boolean active;
    private AmenityDto amenity;
}
