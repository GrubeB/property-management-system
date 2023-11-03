package pl.app.property.organization.service;

import pl.app.common.core.service.SimpleCommandService;
import pl.app.property.organization.model.OrganizationEntity;

import java.util.UUID;

public interface OrganizationService extends
        SimpleCommandService.Updatable<UUID, OrganizationEntity>,
        SimpleCommandService.Creatable<UUID, OrganizationEntity>,
        SimpleCommandService.DeletableById<UUID, OrganizationEntity> {
}
