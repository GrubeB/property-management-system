package pl.app.property.reservation_folio.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolioChargeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddChargeCommand implements Serializable {
    private UUID reservationFolioId;

    private ReservationFolioChargeType type;
    private String name;
    private BigDecimal amount;
    private String current;
    private Boolean shouldByPaidBeforeRegistration;
}