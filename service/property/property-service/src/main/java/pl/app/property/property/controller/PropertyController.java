package pl.app.property.property.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.CommandController;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(PropertyController.resourcePath)
@RequiredArgsConstructor
@Getter
public class PropertyController implements
        CommandController.CreatableWithParent<UUID, PropertyEntity>,
        CommandController.Updatable<UUID, PropertyEntity>,
        CommandController.DeletableById<UUID, PropertyEntity> {
    public static final String resourceName = "properties";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/" + resourceName;
    private final List<String> parentIdPathVariableNames = List.of("organizationId");

    public final PropertyService service;
}
