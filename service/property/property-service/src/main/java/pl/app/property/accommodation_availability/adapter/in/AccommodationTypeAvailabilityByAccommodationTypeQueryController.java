package pl.app.property.accommodation_availability.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.adapter.out.query.AccommodationTypeAvailabilityQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeAvailabilityByAccommodationTypeQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeAvailabilityByAccommodationTypeQueryController implements
        QueryController.FetchableWithFilter<UUID, AccommodationTypeAvailabilityEntity> {

    public static final String resourceName = "accommodation-type-availabilities";
    public static final String resourcePath = "/api/v1/accommodation-types/{accommodationTypeId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "accommodationTypeId", "accommodationType.accommodationTypeId"
    );

    private final AccommodationTypeAvailabilityQueryService service;
}
