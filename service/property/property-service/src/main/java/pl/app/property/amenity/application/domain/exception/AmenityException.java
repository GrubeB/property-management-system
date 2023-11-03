package pl.app.property.amenity.application.domain.exception;


import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface AmenityException {
    class NotFoundAmenitiesCategoryException extends NotFoundException {
        public NotFoundAmenitiesCategoryException() {
            super("not found amenity category");
        }

        public NotFoundAmenitiesCategoryException(String message) {
            super(message);
        }

        public static NotFoundAmenitiesCategoryException fromCategoryName(String categoryName) {
            return new NotFoundAmenitiesCategoryException("not found amenities category with name: " + categoryName);
        }
    }

    class NotFoundAmenityException extends NotFoundException {
        public NotFoundAmenityException() {
            super("not found amenity");
        }

        public NotFoundAmenityException(String message) {
            super(message);
        }

        public static NotFoundAmenityException fromId(UUID amenityId) {
            return new NotFoundAmenityException("not found amenity with id: " + amenityId);
        }
    }

    class NotFoundOrganizationAmenityException extends NotFoundException {
        public NotFoundOrganizationAmenityException() {
            super("not found organization amenity");
        }

        public NotFoundOrganizationAmenityException(String message) {
            super(message);
        }

        public static NotFoundAmenityException fromId(UUID amenityId) {
            return new NotFoundAmenityException("not found organization amenity with id: " + amenityId);
        }
    }
}
