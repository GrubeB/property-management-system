package pl.app.property.organization.exception;


import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface OrganizationException {
    class NotFoundOrganizationException extends NotFoundException {
        public NotFoundOrganizationException() {
            super("not found organization");
        }

        public NotFoundOrganizationException(String message) {
            super(message);
        }

        public static NotFoundOrganizationException fromId(UUID organizationId) {
            return new NotFoundOrganizationException("Not found organization with id: " + organizationId);
        }
    }

}
