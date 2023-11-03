package pl.app.property.accommodation_price_policy.exception;


import pl.app.common.core.service.exception.IllegalArgumentException;
import pl.app.common.core.service.exception.NotFoundException;

import java.util.UUID;

public interface AccommodationTypePricePolicyException {
    class NotFoundAccommodationTypePriceDayOfTheWeekPolicyException extends NotFoundException {
        public NotFoundAccommodationTypePriceDayOfTheWeekPolicyException() {
            super("not found policy");
        }

        public NotFoundAccommodationTypePriceDayOfTheWeekPolicyException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypePriceDayOfTheWeekPolicyException fromId(UUID policyId) {
            return new NotFoundAccommodationTypePriceDayOfTheWeekPolicyException("not found policy with id: " + policyId);
        }
    }

    class NotFoundAccommodationTypePriceNumberOfDaysPolicyException extends NotFoundException {
        public NotFoundAccommodationTypePriceNumberOfDaysPolicyException() {
            super("not found policy");
        }

        public NotFoundAccommodationTypePriceNumberOfDaysPolicyException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypePriceNumberOfDaysPolicyException fromId(UUID policyId) {
            return new NotFoundAccommodationTypePriceNumberOfDaysPolicyException("not found policy with id: " + policyId);
        }
    }

    class AccommodationTypePriceNumberOfDaysPolicyAlreadyExistsException extends IllegalArgumentException {
        public AccommodationTypePriceNumberOfDaysPolicyAlreadyExistsException() {
            super("AccommodationTypePriceNumberOfDaysPolicy already exists for this property");
        }

        public AccommodationTypePriceNumberOfDaysPolicyAlreadyExistsException(String message) {
            super(message);
        }
    }

    class AccommodationTypePriceDayOfTheWeekPolicyAlreadyExistsException extends IllegalArgumentException {
        public AccommodationTypePriceDayOfTheWeekPolicyAlreadyExistsException() {
            super("AccommodationTypePriceDayOfTheWeekPolicy already exists for this property");
        }

        public AccommodationTypePriceDayOfTheWeekPolicyAlreadyExistsException(String message) {
            super(message);
        }
    }
}
