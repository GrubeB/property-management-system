package pl.app.property.accommodation_type.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.accommodation_type.model.BedType;
import pl.app.property.accommodation_type.model.GenderRoomType;
import pl.app.property.accommodation_type.model.RoomType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationTypeCreateDto implements Serializable {
    private BigDecimal defaultPricePerDay;
    private String name;
    private String abbreviation;
    private String description;
    private GenderRoomType genderRoomType;
    private RoomType roomType;
    private Set<AccommodationTypeDto.AccommodationTypeBedDto> beds;
    private Set<AccommodationTypeDto.AccommodationTypeImageDto> images;
    private Set<BaseDto> accommodation;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccommodationTypeBedDto implements Serializable {
        private Integer numberOfBeds;
        private BedType type;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccommodationTypeImageDto implements Serializable {
        private String fileId;
        private String description;
    }
}
