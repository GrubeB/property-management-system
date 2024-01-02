package pl.app.property.guest.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.CommandController;
import pl.app.property.guest.dto.GuestDto;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.guest.service.GuestService;

import java.util.UUID;

@RestController
@RequestMapping(GuestController.resourcePath)
@RequiredArgsConstructor
@Getter
public class GuestController implements
        CommandController.Creatable.DtoCreatableWithParent<UUID, GuestEntity, GuestDto, GuestDto, UUID>,
        CommandController.Updatable.DtoUpdatableWithParent<UUID, GuestEntity, GuestDto, GuestDto, UUID>,
        CommandController.Deletable.SimpleDeletable<UUID, GuestEntity> {
    public static final String resourceName = "guests";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;
    private final String parentIdPathVariableName = "propertyId";
    public final GuestService service;
}
