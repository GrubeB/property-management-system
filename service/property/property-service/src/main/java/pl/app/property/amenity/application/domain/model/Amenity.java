package pl.app.property.amenity.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Amenity {
    private UUID amenityId;
    private String name;
    private String category;
    private String description;
    private Boolean standard; // if is not custom

    public Amenity() {
        this.amenityId = UUID.randomUUID();
    }

    public Amenity(String name, String category, String description, Boolean standard) {
        this.amenityId = UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.description = description;
        this.standard = standard;
    }
}

