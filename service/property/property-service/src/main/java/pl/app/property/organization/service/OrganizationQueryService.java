package pl.app.property.organization.service;

import pl.app.common.core.service.QueryService;
import pl.app.property.organization.model.OrganizationEntity;

import java.util.UUID;

public interface OrganizationQueryService extends QueryService.Fetchable<UUID, OrganizationEntity> {
}
