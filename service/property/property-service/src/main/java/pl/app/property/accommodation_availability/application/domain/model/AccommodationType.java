package pl.app.property.accommodation_availability.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AccommodationType {
    private UUID accommodationTypeId;
    private Property property;
    private List<Accommodation> accommodation;

    @Getter
    @AllArgsConstructor
    public static class Accommodation {
        private UUID accommodationId;
    }

    @Getter
    @AllArgsConstructor
    public static class Property {
        private UUID propertyId;
    }
}
