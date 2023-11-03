package pl.app.property.registration.application.port.out;


import pl.app.property.registration.application.domain.model.Registration;

public interface AccommodationTypeAvailabilityPort {
    boolean isAccommodationTypeAvailable(Registration registration);
}
