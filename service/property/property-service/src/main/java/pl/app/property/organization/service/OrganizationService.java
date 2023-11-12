package pl.app.property.organization.service;

import pl.app.common.core.service.CommandService;
import pl.app.property.organization.dto.OrganizationDto;
import pl.app.property.organization.model.OrganizationEntity;

import java.util.UUID;

public interface OrganizationService extends
        CommandService.Creatable.DtoCreatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandService.Updatable.DtoUpdatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandService.Deletable.SimpleDeletable<UUID, OrganizationEntity> {
}
