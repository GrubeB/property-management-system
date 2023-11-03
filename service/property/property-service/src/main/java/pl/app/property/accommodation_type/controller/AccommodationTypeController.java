package pl.app.property.accommodation_type.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.core.web.CommandController;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.accommodation_type.command.CreateAccommodationTypeCommand;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.service.AccommodationTypeService;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeController implements
        CommandController.Updatable<UUID, AccommodationTypeEntity>,
        CommandController.DeletableById<UUID, AccommodationTypeEntity> {
    public static final String resourceName = "accommodation-types";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    public final AccommodationTypeService service;

    @PostMapping
    ResponseEntity<AccommodationTypeEntity> create(@PathVariable String propertyId, @RequestBody CreateAccommodationTypeCommand command, HttpServletRequest request) {
        AccommodationTypeEntity saved = service.create(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(saved.getAccommodationTypeId(), request.getRequestURI()))
                .body(saved);
    }
}
