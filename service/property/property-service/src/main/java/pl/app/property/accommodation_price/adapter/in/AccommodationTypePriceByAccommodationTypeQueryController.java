package pl.app.property.accommodation_price.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePriceEntity;
import pl.app.property.accommodation_price.adapter.out.query.AccommodationTypePriceQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypePriceByAccommodationTypeQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypePriceByAccommodationTypeQueryController implements
        QueryController.DtoFetchableWithFilter.Full<UUID, AccommodationTypePriceEntity> {
    public static final String resourceName = "accommodation-type-prices";
    public static final String resourcePath = "/api/v1/accommodation-types/{accommodationTypeId}/" + resourceName;

    private final AccommodationTypePriceQueryService service;

    private final Map<String, String> parentFilterMap = Map.of(
            "accommodationTypeId", "accommodationType.accommodationTypeId"
    );
}
