package pl.app.property.organization.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.CommandController;
import pl.app.property.organization.dto.OrganizationDto;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.organization.service.OrganizationService;

import java.util.UUID;

@RestController
@RequestMapping(OrganizationController.resourcePath)
@RequiredArgsConstructor
@Getter
public class OrganizationController implements
        CommandController.Creatable.DtoCreatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandController.Updatable.DtoUpdatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandController.Deletable.SimpleDeletable<UUID, OrganizationEntity> {
    public static final String resourceName = "organizations";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final OrganizationService service;
}
