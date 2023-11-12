package pl.app.property.accommodation.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.accommodation.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation.adapter.out.query.AccommodationQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AccommodationByAccommodationTypeQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationByAccommodationTypeQueryController implements
        QueryController.DtoFetchable.Full<UUID, AccommodationEntity> {
    public static final String resourceName = "accommodations";
    public static final String resourcePath = "/api/v1/accommodation-types/{accommodationTypeId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "accommodationTypeId", "accommodationType.accommodationTypeId"
    );

    public final AccommodationQueryService service;
}
