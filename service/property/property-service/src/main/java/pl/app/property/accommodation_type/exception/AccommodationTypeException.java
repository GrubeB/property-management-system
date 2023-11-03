package pl.app.property.accommodation_type.exception;


import pl.app.common.core.service.exception.NotFoundException;


public interface AccommodationTypeException {
    class NotFoundAccommodationTypeException extends NotFoundException {
        public NotFoundAccommodationTypeException() {
            super("not found accommodation type");
        }

        public NotFoundAccommodationTypeException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypeException fromId(String accommodationTypeId) {
            return new NotFoundAccommodationTypeException("Not found accommodation type with id: " + accommodationTypeId);
        }
    }
}
