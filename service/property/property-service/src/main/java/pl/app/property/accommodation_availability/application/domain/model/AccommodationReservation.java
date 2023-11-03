package pl.app.property.accommodation_availability.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.property.accommodation_availability.application.domain.exception.AccommodationAvailabilityException;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@AllArgsConstructor
public class AccommodationReservation {
    private UUID accommodationReservationId;

    private UUID accommodationId;
    private AccommodationStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private AccommodationTypeReservation accommodationTypeReservation;

    public AccommodationReservation(UUID accommodationId, AccommodationStatus status, LocalDate startDate, LocalDate endDate) {
        this.accommodationReservationId = UUID.randomUUID();
        this.accommodationId = accommodationId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void changeStatusTo(AccommodationStatus newStatus) {
        if (this.status.equals(newStatus)) {
            return;
        }
        switch (this.status) {
            case RESERVED -> {
                if (AccommodationStatus.CHECK_IN.equals(newStatus)
                        || AccommodationStatus.BLOCKED.equals(newStatus)) {
                    this.status = newStatus;
                } else {
                    throw new AccommodationAvailabilityException.AccommodationReservationValidationException();
                }
            }
            case CHECK_IN -> {
                if (AccommodationStatus.CHECK_OUT.equals(newStatus)) {
                    this.status = newStatus;
                } else {
                    throw new AccommodationAvailabilityException.AccommodationReservationValidationException();
                }
            }
            case CHECK_OUT -> {
                throw new AccommodationAvailabilityException.AccommodationReservationValidationException();
            }
            case BLOCKED -> {
                if (AccommodationStatus.RESERVED.equals(newStatus)) {
                    this.status = newStatus;
                } else {
                    throw new AccommodationAvailabilityException.AccommodationReservationValidationException();
                }
            }
        }
    }

    public void setAccommodationTypeReservation(AccommodationTypeReservation accommodationTypeReservation) {
        this.accommodationTypeReservation = accommodationTypeReservation;
    }
}
