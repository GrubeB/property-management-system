package pl.app.property.accommodation_availability.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccommodationReservationCommand implements Serializable {
    private UUID accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private AccommodationStatus status;
}