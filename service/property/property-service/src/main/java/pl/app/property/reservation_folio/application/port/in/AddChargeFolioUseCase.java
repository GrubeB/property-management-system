package pl.app.property.reservation_folio.application.port.in;

import java.util.UUID;

public interface AddChargeFolioUseCase {
    UUID addCharge(AddChargeCommand command);
}
