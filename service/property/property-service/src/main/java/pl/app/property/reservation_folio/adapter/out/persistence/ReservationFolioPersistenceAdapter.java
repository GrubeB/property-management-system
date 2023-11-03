package pl.app.property.reservation_folio.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.reservation_folio.adapter.out.persistence.mapper.ReservationFolioMapper;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioEntity;
import pl.app.property.reservation_folio.adapter.out.persistence.repository.ReservationFolioRepository;
import pl.app.property.reservation_folio.application.domain.exception.ReservationFolioException;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolio;
import pl.app.property.reservation_folio.application.port.out.LoadReservationFolioPort;
import pl.app.property.reservation_folio.application.port.out.SaveReservationFolioPort;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class ReservationFolioPersistenceAdapter implements
        LoadReservationFolioPort,
        SaveReservationFolioPort {
    private final ReservationFolioRepository reservationFolioRepository;
    private final ReservationFolioMapper reservationFolioMapper;

    @Override
    public ReservationFolio loadRegistrationFolio(UUID reservationFolioId) {
        ReservationFolioEntity entity = reservationFolioRepository.findById(reservationFolioId)
                .orElseThrow(() -> ReservationFolioException.NotFoundReservationFolioException.fromId(reservationFolioId));
        return reservationFolioMapper.mapToReservationFolio(entity);
    }

    @Override
    public UUID saveRegistrationFolio(ReservationFolio reservationFolio) {
        ReservationFolioEntity entity = reservationFolioMapper.mapToReservationFolioEntity(reservationFolio);
        reservationFolioRepository.save(entity);
        return entity.getReservationFolioId();
    }
}
