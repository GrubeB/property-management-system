package pl.app.property.accommodation_type.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.accommodation_type.model.BedType;
import pl.app.property.accommodation_type.model.GenderRoomType;
import pl.app.property.accommodation_type.model.RoomType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccommodationTypeCommand implements Serializable {
    private UUID propertyId;
    private String name;
    private String abbreviation;
    private String description;
    private BigDecimal defaultPricePerDay;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
    private Set<Bed> beds;
    private Set<Image> images;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bed {
        private Integer numberOfBeds;
        private BedType type;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image {
        private String fileId;
        private String description;
    }
}
