package pl.app.property.accommodation_type.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.accommodation_type.model.BedType;
import pl.app.property.accommodation_type.model.GenderRoomType;
import pl.app.property.accommodation_type.model.RoomType;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationTypeDto implements Serializable {
    private UUID accommodationTypeId;
    private String name;
    private String abbreviation;
    private String description;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
    private Set<AccommodationTypeBedDto> beds;
    private Set<AccommodationTypeImageDto> images;
    private Set<BaseDto> accommodation;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccommodationTypeBedDto implements Serializable {
        private UUID bedId;
        private Integer numberOfBeds;
        private BedType type;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccommodationTypeImageDto implements Serializable {
        private UUID imageId;
        private String fileId;
        private String description;
    }
}
