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
public class PropertyAmenityDto implements Serializable {
    private UUID propertyAmenityId;
    private UUID propertyId;
    private AmenityDto amenity;
}
