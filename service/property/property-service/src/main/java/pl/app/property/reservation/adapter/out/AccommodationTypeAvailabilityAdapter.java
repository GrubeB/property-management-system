package pl.app.property.reservation.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.accommodation_availability.application.port.in.IsAccommodationTypeAvailableCommand;
import pl.app.property.accommodation_availability.application.port.in.IsAccommodationTypeAvailableUseCase;
import pl.app.property.reservation.application.domain.model.Reservation;
import pl.app.property.reservation.application.port.out.AccommodationTypeAvailabilityPort;

@Service
@RequiredArgsConstructor
class AccommodationTypeAvailabilityAdapter implements
        AccommodationTypeAvailabilityPort {

    private final IsAccommodationTypeAvailableUseCase isAccommodationTypeAvailableUseCase;

    @Override
    public boolean isAccommodationTypeAvailable(Reservation reservation) {
        return reservation.getReservationBookings().stream()
                .map(booking -> new IsAccommodationTypeAvailableCommand(
                        booking.getAccommodationTypeId(),
                        booking.getStart(),
                        booking.getEnd(),
                        1))
                .allMatch(isAccommodationTypeAvailableUseCase::isAccommodationTypeAvailable);
    }
}
