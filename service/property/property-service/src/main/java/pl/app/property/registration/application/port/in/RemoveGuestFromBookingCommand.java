package pl.app.property.registration.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveGuestFromBookingCommand implements Serializable {
    private UUID registrationId;
    private UUID bookingId;
    private List<UUID> guestIds;
}