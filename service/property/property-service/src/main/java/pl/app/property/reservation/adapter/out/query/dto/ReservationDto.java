package pl.app.property.reservation.adapter.out.query.dto;

import lombok.*;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.guest.dto.GuestDto;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationBookingEntity;
import pl.app.property.reservation.application.domain.model.ReservationSource;
import pl.app.property.reservation.application.domain.model.ReservationStatus;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioEntity;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto implements Serializable {
    private UUID reservationId;
    private ReservationStatus status;
    private ReservationSource source;
    private ReservationFolioEntity reservationFolio;
    private BaseDto mainGuest;
    private Set<BaseDto> guests;
    private Set<ReservationBookingEntity> accommodationTypeBookings;
}
