package pl.app.property.accommodation_type.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.service.AccommodationTypeQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeByPropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeByPropertyQueryController implements
        QueryController.DtoFetchableWithFilter.Full<UUID, AccommodationTypeEntity> {
    public static final String resourceName = "accommodation-types";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "propertyId", "property.propertyId"
    );

    public final AccommodationTypeQueryService service;
}
