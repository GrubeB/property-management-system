package pl.app.property.organization.persistence;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import pl.app.property.organization.model.OrganizationEntity;

public class OrganizationEntityListener {
    @PrePersist
    @PreUpdate
    private void beforeAnyUpdate(OrganizationEntity entity) {
        entity.getOrganizationImages().forEach(image -> image.setOrganization(entity));
    }
}
