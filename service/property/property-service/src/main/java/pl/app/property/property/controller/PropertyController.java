package pl.app.property.property.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.CommandController;
import pl.app.property.property.dto.PropertyDto;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyService;

import java.util.UUID;

@RestController
@RequestMapping(PropertyController.resourcePath)
@RequiredArgsConstructor
@Getter
public class PropertyController implements
        CommandController.Creatable.DtoCreatableWithParent<UUID, PropertyEntity, PropertyDto, PropertyDto, UUID>,
        CommandController.Updatable.DtoUpdatableWithParent<UUID, PropertyEntity, PropertyDto, PropertyDto, UUID>,
        CommandController.Deletable.SimpleDeletable<UUID, PropertyEntity> {
    public static final String resourceName = "properties";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/" + resourceName;
    private final String parentIdPathVariableName = "organizationId";

    public final PropertyService service;
}
