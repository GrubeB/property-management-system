package pl.app.property.registration_folio.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveChargeFromPartyFolioCommand implements Serializable {
    private UUID registrationFolioId;
    private UUID chargeId;
}