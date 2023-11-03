package pl.app.property.registration_folio.application.port.out;


import pl.app.property.registration_folio.application.domain.model.RegistrationFolio;

import java.util.UUID;

public interface LoadRegistrationFolioPort {
    RegistrationFolio loadRegistrationFolio(UUID registrationFolioId);

    RegistrationFolio loadRegistrationFolioByPartyFolio(UUID partyFolioId);
}
