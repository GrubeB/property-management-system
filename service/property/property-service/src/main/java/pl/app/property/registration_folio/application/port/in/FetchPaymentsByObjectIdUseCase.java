package pl.app.property.registration_folio.application.port.in;

import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioCharge;

import java.util.List;

public interface FetchPaymentsByObjectIdUseCase {
    List<RegistrationPartyFolioCharge> fetchChargesByObjectId(FetchChargesByObjectIdCommand command);
}
