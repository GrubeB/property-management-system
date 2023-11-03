package pl.app.property.registration.application.port.in;

import java.util.UUID;

public interface CreateRegistrationUseCase {
    UUID createRegistration(CreateRegistrationCommand command);
}
