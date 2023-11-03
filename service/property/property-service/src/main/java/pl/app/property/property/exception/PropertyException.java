package pl.app.property.property.exception;


import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface PropertyException {
    class NotFoundPropertyException extends NotFoundException {
        public NotFoundPropertyException() {
            super("not found property");
        }

        public NotFoundPropertyException(String message) {
            super(message);
        }

        public static NotFoundPropertyException fromId(UUID propertyId) {
            return new NotFoundPropertyException("not found property with id: " + propertyId);
        }
    }
}
