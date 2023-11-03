package pl.app.property.registration.application.port.out;

import pl.app.property.registration.application.domain.model.Registration;

import java.util.UUID;

public interface LoadRegistrationPort {
    Registration loadRegistration(UUID registrationId);
}
