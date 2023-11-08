package pl.app.property.reservation.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.guest.service.GuestQueryService;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationBookingEntity;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationEntity;
import pl.app.property.reservation.application.domain.model.Reservation;
import pl.app.property.reservation.application.domain.model.ReservationBooking;
import pl.app.property.reservation.application.domain.model.ReservationGuest;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioEntity;
import pl.app.property.reservation_folio.adapter.out.persistence.repository.ReservationFolioRepository;
import pl.app.property.reservation_folio.application.domain.exception.ReservationFolioException;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationMapper {
    private final GuestQueryService guestQueryService;
    private final PropertyQueryService propertyQueryService;
    private final ReservationFolioRepository reservationFolioRepository;

    public Reservation mapToReservation(ReservationEntity entity) {
        return new Reservation(
                entity.getReservationId(),
                entity.getProperty().getPropertyId(),
                entity.getStatus(),
                entity.getSource(),
                this.mapToReservationGuest(entity.getMainGuest()),
                entity.getGuests().stream().map(this::mapToReservationGuest).collect(Collectors.toList()),
                entity.getAccommodationTypeBookings().stream().map(this::mapToAccommodationTypeBooking).collect(Collectors.toList()),
                entity.getReservationFolio().getReservationFolioId(),
                entity.getCreatedDate()
        );
    }

    private ReservationBooking mapToAccommodationTypeBooking(ReservationBookingEntity entity) {
        return new ReservationBooking(
                entity.getReservationBookingId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getAccommodationTypeId(),
                entity.getNumberOfAdults(),
                entity.getNumberOfChildren(),
                entity.getAccommodationTypeReservationTypeId()
        );
    }

    private ReservationGuest mapToReservationGuest(GuestEntity entity) {
        return new ReservationGuest(
                entity.getGuestId()
        );
    }

    public ReservationEntity mapToReservationEntity(Reservation domain) {
        ReservationEntity entity = ReservationEntity.builder()
                .reservationId(domain.getReservationId())
                .property(mapToPropertyEntity(domain.getPropertyId()))
                .status(domain.getStatus())
                .source(domain.getSource())
                .mainGuest(this.mapToGuestEntity(domain.getMainGuest()))
                .guests(domain.getOtherGuests().stream().map(this::mapToGuestEntity).collect(Collectors.toSet()))
                .accommodationTypeBookings(domain.getReservationBookings().stream().map(this::mapToAccommodationTypeBookingEntity).collect(Collectors.toSet()))
                .reservationFolio(this.mapToReservationFolioEntity(domain.getReservationFolioId()))
                .build();
        entity.getAccommodationTypeBookings().forEach(booking -> booking.setReservation(entity));
        return entity;
    }

    private ReservationFolioEntity mapToReservationFolioEntity(UUID reservationFolioId) {
        return reservationFolioId == null ? null : reservationFolioRepository.findById(reservationFolioId)
                .orElseThrow(() -> ReservationFolioException.NotFoundReservationFolioException.fromId(reservationFolioId));
    }

    private ReservationBookingEntity mapToAccommodationTypeBookingEntity(ReservationBooking domain) {
        return ReservationBookingEntity.builder()
                .reservationBookingId(domain.getReservationBookingId())
                .startDate(domain.getStart())
                .endDate(domain.getEnd())
                .accommodationTypeId(domain.getAccommodationTypeId())
                .numberOfAdults(domain.getNumberOfAdults())
                .numberOfChildren(domain.getNumberOfChildren())
                .accommodationTypeReservationTypeId(domain.getAccommodationTypeReservationId())
                .build();
    }

    private GuestEntity mapToGuestEntity(ReservationGuest reservationGuest) {
        return guestQueryService.fetchById(reservationGuest.getGuestId());
    }

    private PropertyEntity mapToPropertyEntity(UUID propertyId) {
        return propertyQueryService.fetchById(propertyId);
    }
}
