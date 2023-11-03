package pl.app.property.reservation.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.reservation.adapter.out.persistence.mapper.ReservationMapper;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationEntity;
import pl.app.property.reservation.adapter.out.persistence.repository.ReservationRepository;
import pl.app.property.reservation.application.domain.exception.ReservationException;
import pl.app.property.reservation.application.domain.model.Reservation;
import pl.app.property.reservation.application.domain.model.ReservationStatus;
import pl.app.property.reservation.application.port.out.LoadReservationsPort;
import pl.app.property.reservation.application.port.out.SaveReservationsPort;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationPersistenceAdapter implements
        LoadReservationsPort,
        SaveReservationsPort {
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation loadReservation(UUID reservationId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> ReservationException.NotFoundReservationException.fromId(reservationId));
        return reservationMapper.mapToReservation(reservationEntity);
    }

    @Override
    public List<UUID> loadConfirmedReservationIds(UUID propertyId) {
        return reservationRepository.findByStatusAndCreatedDateLessThanEqualAndProperty_PropertyId(ReservationStatus.CONFIRMED,
                Instant.now(), propertyId);
    }

    @Override
    public List<UUID> loadPendingReservationIdsCreatedDayBefore(UUID propertyId) {
        return reservationRepository.findByStatusAndCreatedDateLessThanEqualAndProperty_PropertyId(ReservationStatus.PENDING,
                Instant.now().minus(1, ChronoUnit.DAYS), propertyId);
    }

    @Override
    public UUID saveReservation(Reservation reservation) {
        ReservationEntity reservationEntity = reservationMapper.mapToReservationEntity(reservation);
        return reservationRepository.saveAndFlush(reservationEntity).getReservationId();
    }
}
