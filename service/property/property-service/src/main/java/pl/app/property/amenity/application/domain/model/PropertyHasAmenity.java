package pl.app.property.amenity.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PropertyHasAmenity {
    private UUID propertyAmenityId;
    private Amenity amenity;

    public PropertyHasAmenity(Amenity amenity) {
        this.propertyAmenityId = UUID.randomUUID();
        this.amenity = amenity;
    }
}
