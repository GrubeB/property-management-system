package pl.app.property.guest.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.CommandController;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.guest.service.GuestService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(GuestController.resourcePath)
@RequiredArgsConstructor
@Getter
public class GuestController implements
        CommandController.CreatableWithParent<UUID, GuestEntity>,
        CommandController.Updatable<UUID, GuestEntity>,
        CommandController.DeletableById<UUID, GuestEntity> {
    public static final String resourceName = "guests";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;
    private final List<String> parentIdPathVariableNames = List.of("propertyId");
    public final GuestService service;
}
