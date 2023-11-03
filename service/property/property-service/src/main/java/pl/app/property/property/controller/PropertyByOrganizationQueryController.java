package pl.app.property.property.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(PropertyByOrganizationQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class PropertyByOrganizationQueryController implements
        QueryController.DtoFetchableWithFilter<UUID, PropertyEntity> {
    public static final String resourceName = "properties";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "organizationId", "organization.organizationId"
    );

    public final PropertyQueryService service;
}
