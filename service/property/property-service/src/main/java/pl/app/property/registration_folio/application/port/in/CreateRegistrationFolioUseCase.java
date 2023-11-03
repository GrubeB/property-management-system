package pl.app.property.registration_folio.application.port.in;

import java.util.UUID;

public interface CreateRegistrationFolioUseCase {
    UUID createRegistrationFolio(CreateRegistrationFolioCommand command);
}
