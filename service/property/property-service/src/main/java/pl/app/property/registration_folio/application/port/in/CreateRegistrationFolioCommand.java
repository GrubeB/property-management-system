package pl.app.property.registration_folio.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegistrationFolioCommand implements Serializable {
    private UUID registrationId;
    private UUID propertyId;
    private List<UUID> partyIds;
}