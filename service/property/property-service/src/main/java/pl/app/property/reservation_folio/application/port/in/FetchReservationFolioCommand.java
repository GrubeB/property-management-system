package pl.app.property.reservation_folio.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FetchReservationFolioCommand implements Serializable {
    private UUID reservationFolioId;
}