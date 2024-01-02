package pl.app.property.registration_folio.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefundRegistrationFolioCommand implements Serializable {
    private UUID registrationFolioId;
}