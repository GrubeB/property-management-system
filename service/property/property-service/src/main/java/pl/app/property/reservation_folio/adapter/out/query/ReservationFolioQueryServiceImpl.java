package pl.app.property.reservation_folio.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.reservation_folio.adapter.out.persistence.repository.ReservationFolioRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class ReservationFolioQueryServiceImpl implements ReservationFolioQueryService {
    private final ReservationFolioRepository repository;
    private final ReservationFolioRepository specificationRepository;
}
