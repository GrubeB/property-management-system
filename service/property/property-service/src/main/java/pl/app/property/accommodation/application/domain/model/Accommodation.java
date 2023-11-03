package pl.app.property.accommodation.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Accommodation {
    private UUID accommodationId;
    private String name;
    private String description;
    private UUID accommodationTypeId;

    public Accommodation(String name, String description, UUID accommodationTypeId) {
        this.accommodationId = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.accommodationTypeId = accommodationTypeId;
    }
}
