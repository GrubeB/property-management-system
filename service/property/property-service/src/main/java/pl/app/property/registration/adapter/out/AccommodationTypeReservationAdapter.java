package pl.app.property.registration.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.RemoveAccommodationTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.in.RemoveAccommodationTypeReservationUseCase;
import pl.app.property.registration.application.domain.model.RegistrationBooking;
import pl.app.property.registration.application.port.out.AccommodationTypeReservationPort;

import java.util.UUID;


@Service(value = "pl.app.property.registration.adapter.out.AccommodationTypeReservationAdapter")
@RequiredArgsConstructor
class AccommodationTypeReservationAdapter implements AccommodationTypeReservationPort {
    private final CreateAccommodationTypeReservationUseCase createAccommodationTypeReservationUseCase;
    private final RemoveAccommodationTypeReservationUseCase removeAccommodationTypeReservationUseCase;

    @Override
    public UUID reserveAccommodationType(RegistrationBooking booking) {
        CreateAccommodationTypeReservationCommand command = new CreateAccommodationTypeReservationCommand(
                booking.getAccommodationTypeId(),
                booking.getStart(),
                booking.getEnd()
        );
        return createAccommodationTypeReservationUseCase.createAccommodationTypeReservation(command);
    }

    @Override
    public void releaseAccommodationTypeReservation(RegistrationBooking booking) {
        RemoveAccommodationTypeReservationCommand command = new RemoveAccommodationTypeReservationCommand(booking.getAccommodationTypeReservationId());
        removeAccommodationTypeReservationUseCase.removeAccommodationTypeReservation(command);
    }
}
