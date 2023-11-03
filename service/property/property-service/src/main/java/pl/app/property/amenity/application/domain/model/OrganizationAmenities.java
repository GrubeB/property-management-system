package pl.app.property.amenity.application.domain.model;

import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class OrganizationAmenities {
    private final UUID organizationId;
    private final List<OrganizationHasAmenity> organizationHasAmenities;

    public OrganizationAmenities(UUID organizationId, List<OrganizationHasAmenity> organizationHasAmenities) {
        this.organizationId = organizationId;
        this.organizationHasAmenities = organizationHasAmenities;
    }

    public boolean addAmenity(Amenity amenity) {
        if (this.organizationHasAmenities.stream().anyMatch(organizationHasAmenity -> organizationHasAmenity.getAmenity().equals(amenity))) {
            return false;
        }
        return this.organizationHasAmenities.add(new OrganizationHasAmenity(amenity, true));
    }

    public boolean removeAmenity(Amenity amenity) {
        return this.organizationHasAmenities.removeIf(organizationHasAmenity ->
                organizationHasAmenity.getAmenity().getAmenityId().equals(amenity.getAmenityId()));
    }

    public boolean activateAmenity(Amenity amenity) {
        Optional<OrganizationHasAmenity> any = this.organizationHasAmenities.stream()
                .filter(organizationHasAmenity -> organizationHasAmenity.getAmenity().equals(amenity)).findAny();
        if (any.isPresent()) {
            any.get().activate();
            return true;
        } else {
            return false;
        }
    }

    public boolean activateAmenityById(UUID amenityId) {
        Optional<OrganizationHasAmenity> any = this.organizationHasAmenities.stream()
                .filter(organizationHasAmenity -> organizationHasAmenity.getAmenity().getAmenityId().equals(amenityId)).findAny();
        if (any.isPresent()) {
            any.get().activate();
            return true;
        } else {
            return false;
        }
    }

    public boolean deactivateAmenity(Amenity amenity) {
        Optional<OrganizationHasAmenity> any = this.organizationHasAmenities.stream()
                .filter(organizationHasAmenity -> organizationHasAmenity.getAmenity().equals(amenity)).findAny();
        if (any.isPresent()) {
            any.get().deactivate();
            return true;
        } else {
            return false;
        }
    }

    public boolean deactivateAmenityById(UUID amenityId) {
        Optional<OrganizationHasAmenity> any = this.organizationHasAmenities.stream()
                .filter(organizationHasAmenity -> organizationHasAmenity.getAmenity().getAmenityId().equals(amenityId)).findAny();
        if (any.isPresent()) {
            any.get().deactivate();
            return true;
        } else {
            return false;
        }
    }
}
