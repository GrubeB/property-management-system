package pl.app.property.amenity.application.domain.model;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class PropertyAmenities {
    private final UUID propertyId;
    private final List<PropertyHasAmenity> propertyHasAmenities;

    public PropertyAmenities(UUID organizationId, List<PropertyHasAmenity> organizationHasAmenities) {
        this.propertyId = organizationId;
        this.propertyHasAmenities = organizationHasAmenities;
    }

    public boolean addAmenity(Amenity amenity) {
        if (this.propertyHasAmenities.stream().anyMatch(organizationHasAmenity -> organizationHasAmenity.getAmenity().equals(amenity))) {
            return false;
        }
        return this.propertyHasAmenities.add(new PropertyHasAmenity(amenity));
    }

    public boolean removeAmenity(Amenity amenity) {
        return this.propertyHasAmenities.removeIf(organizationHasAmenity ->
                organizationHasAmenity.getAmenity().getAmenityId().equals(amenity.getAmenityId()));
    }
}
