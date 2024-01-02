package pl.app.property.organization.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.organization.service.OrganizationQueryService;

import java.util.UUID;

@RestController
@RequestMapping(OrganizationQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class OrganizationQueryController implements
        QueryController.DtoFetchable.Full<UUID, OrganizationEntity> {
    public static final String resourceName = "organizations";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final OrganizationQueryService service;
}
