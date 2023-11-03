package pl.app.property.registration_folio.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddPaymentToPartyFolioCommand implements Serializable {
    private UUID partyFolioId;
    private UUID guestId;
    private BigDecimal amount;
    private String current;
}