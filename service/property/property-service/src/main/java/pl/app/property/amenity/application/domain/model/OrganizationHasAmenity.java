package pl.app.property.amenity.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrganizationHasAmenity {
    private UUID organizationAmenityId;
    private Amenity amenity;
    private Boolean active;

    public OrganizationHasAmenity(Amenity amenity, Boolean active) {
        this.organizationAmenityId = UUID.randomUUID();
        this.amenity = amenity;
        this.active = active;
    }

    public boolean activate() {
        if (!this.active) {
            this.active = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean deactivate() {
        if (this.active) {
            this.active = false;
            return true;
        } else {
            return false;
        }
    }
}
