package pl.app.property.reservation.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RejectNoConfirmedReservationsCommand implements Serializable {
    private UUID propertyId;
}