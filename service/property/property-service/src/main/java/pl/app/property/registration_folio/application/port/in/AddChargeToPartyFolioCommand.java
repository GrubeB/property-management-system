package pl.app.property.registration_folio.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioChargeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddChargeToPartyFolioCommand implements Serializable {
    private UUID registrationFolioId;
    private UUID partyId;

    private RegistrationPartyFolioChargeType type;
    private String name;
    private BigDecimal amount;
    private String current;
}