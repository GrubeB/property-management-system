package pl.app.property.registration.application.port.out;


import pl.app.property.registration.application.domain.model.RegistrationBooking;

public interface AccommodationTypeAvailabilityPort {
    boolean isAccommodationTypeAvailable(RegistrationBooking booking);
}
