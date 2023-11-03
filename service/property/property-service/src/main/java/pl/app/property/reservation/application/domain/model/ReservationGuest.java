package pl.app.property.reservation.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReservationGuest {
    private UUID guestId;
}
