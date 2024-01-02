package pl.app.property.registration.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.accommodation_availability.application.port.in.IsAccommodationTypeAvailableCommand;
import pl.app.property.accommodation_availability.application.port.in.IsAccommodationTypeAvailableUseCase;
import pl.app.property.registration.application.domain.model.RegistrationBooking;
import pl.app.property.registration.application.port.out.AccommodationTypeAvailabilityPort;

@Service(value = "pl.app.property.registration.adapter.out.AccommodationTypeAvailabilityAdapter")
@RequiredArgsConstructor
class AccommodationTypeAvailabilityAdapter implements
        AccommodationTypeAvailabilityPort {

    private final IsAccommodationTypeAvailableUseCase isAccommodationTypeAvailableUseCase;

    public boolean isAccommodationTypeAvailable(RegistrationBooking registration) {
        return isAccommodationTypeAvailableUseCase.isAccommodationTypeAvailable(new IsAccommodationTypeAvailableCommand(
                registration.getAccommodationTypeId(),
                registration.getStart(),
                registration.getEnd(),
                1));
    }
}
