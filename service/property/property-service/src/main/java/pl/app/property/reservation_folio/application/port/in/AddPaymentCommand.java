package pl.app.property.reservation_folio.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddPaymentCommand implements Serializable {
    private UUID reservationFolioId;

    private UUID guestId;
    private BigDecimal amount;
    private String current;
}