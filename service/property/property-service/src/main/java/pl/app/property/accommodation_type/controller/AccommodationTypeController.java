package pl.app.property.accommodation_type.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.CommandController;
import pl.app.property.accommodation_type.dto.AccommodationTypeCreateDto;
import pl.app.property.accommodation_type.dto.AccommodationTypeDto;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.service.AccommodationTypeService;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeController implements
        CommandController.Creatable.DtoCreatableWithParent<UUID, AccommodationTypeEntity, AccommodationTypeCreateDto, AccommodationTypeDto, UUID>,
        CommandController.Updatable.DtoUpdatableWithParent<UUID, AccommodationTypeEntity, AccommodationTypeDto, AccommodationTypeDto, UUID>,
        CommandController.Deletable.SimpleDeletable<UUID, AccommodationTypeEntity> {

    public static final String resourceName = "accommodation-types";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;
    public final String parentIdPathVariableName = "propertyId";

    public final AccommodationTypeService service;
}
