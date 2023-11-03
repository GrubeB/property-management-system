package pl.app.property.registration.application.port.out;


import pl.app.property.registration.application.domain.model.Registration;

import java.util.UUID;

public interface SaveRegistrationPort {
    UUID saveRegistration(Registration registration);
}
