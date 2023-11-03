package pl.app.property.registration.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.accommodation_availability.application.port.in.IsAccommodationTypeAvailableCommand;
import pl.app.property.accommodation_availability.application.port.in.IsAccommodationTypeAvailableUseCase;
import pl.app.property.registration.application.domain.model.Registration;
import pl.app.property.registration.application.port.out.AccommodationTypeAvailabilityPort;

@Service(value = "pl.app.property.registration.adapter.out.AccommodationTypeAvailabilityAdapter")
@RequiredArgsConstructor
class AccommodationTypeAvailabilityAdapter implements
        AccommodationTypeAvailabilityPort {

    private final IsAccommodationTypeAvailableUseCase isAccommodationTypeAvailableUseCase;

    @Override
    public boolean isAccommodationTypeAvailable(Registration registration) {
        return registration.getRegistrationBookings().stream()
                .map(registrationBooking -> new IsAccommodationTypeAvailableCommand(
                        registrationBooking.getAccommodationTypeId(),
                        registrationBooking.getStart(),
                        registrationBooking.getEnd(),
                        1))
                .allMatch(isAccommodationTypeAvailableUseCase::isAccommodationTypeAvailable);
    }
}
