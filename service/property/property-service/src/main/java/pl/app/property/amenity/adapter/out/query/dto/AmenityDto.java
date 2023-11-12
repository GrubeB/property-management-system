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
public class AmenityDto implements Serializable {
    private UUID amenityId;
    private String name;
    private AmenityCategoryDto amenityCategory;
    private String description;
    private Boolean standard;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AmenityCategoryDto implements Serializable {
        private String name;
    }

}
