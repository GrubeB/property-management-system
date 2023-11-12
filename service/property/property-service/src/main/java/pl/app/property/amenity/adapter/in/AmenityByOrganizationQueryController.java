package pl.app.property.amenity.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.amenity.adapter.out.persistence.model.OrganizationAmenityEntity;
import pl.app.property.amenity.adapter.out.query.OrganizationAmenityQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AmenityByOrganizationQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AmenityByOrganizationQueryController implements
        QueryController.DtoFetchable.Full<UUID, OrganizationAmenityEntity> {
    public static final String resourceName = "amenities";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "organizationId", "organizationId"
    );
    private final OrganizationAmenityQueryService service;
}
