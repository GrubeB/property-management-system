package pl.app.property.property.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.property.model.PropertyType;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto implements Serializable {
    private UUID propertyId;
    private PropertyType propertyType;
    private LocalTime checkInFromTime;
    private LocalTime checkInToTime;
    private LocalTime checkOutFromTime;
    private LocalTime checkOutToTime;
    private Set<PropertyImageDto> propertyImages;
    private PropertyDetailsDto propertyDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PropertyImageDto implements Serializable {
        private UUID imageId;
        private String fileId;
        private String description;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PropertyDetailsDto implements Serializable {
        private UUID propertyDetailsId;
        private String name;
        private String email;
        private String phone;
        private String website;
        private String description;
        private PropertyAddressDto propertyAddress;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PropertyAddressDto implements Serializable {
        private UUID addressId;
        private String address1;
        private String address2;
        private String city;
        private String country;
        private String region;
        private String zipCode;
    }
}
