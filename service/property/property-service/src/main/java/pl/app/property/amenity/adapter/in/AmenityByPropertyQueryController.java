package pl.app.property.amenity.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.amenity.adapter.out.persistence.model.PropertyAmenityEntity;
import pl.app.property.amenity.adapter.out.query.PropertyAmenityQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AmenityByPropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AmenityByPropertyQueryController implements
        QueryController.DtoFetchable.Full<UUID, PropertyAmenityEntity> {
    public static final String resourceName = "amenities";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;
    private final Map<String, String> parentFilterMap = Map.of(
            "propertyId", "propertyId"
    );
    private final PropertyAmenityQueryService service;
}
