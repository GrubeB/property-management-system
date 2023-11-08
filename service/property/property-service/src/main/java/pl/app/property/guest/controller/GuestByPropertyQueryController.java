package pl.app.property.guest.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.guest.service.GuestQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(GuestByPropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class GuestByPropertyQueryController implements
        QueryController.DtoFetchableWithFilter<UUID, GuestEntity> {
    public static final String resourceName = "guests";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;
    private final Map<String, String> parentFilterMap = Map.of(
            "propertyId", "property.propertyId"
    );
    public final GuestQueryService service;
}
