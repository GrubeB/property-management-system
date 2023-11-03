package pl.app.property.registration.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegistrationGuest {
    private UUID guestId;
}
