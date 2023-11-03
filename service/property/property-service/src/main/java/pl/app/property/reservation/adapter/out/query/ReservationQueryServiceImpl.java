package pl.app.property.reservation.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.reservation.adapter.out.persistence.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class ReservationQueryServiceImpl implements ReservationQueryService {
    private final ReservationRepository repository;
    private final ReservationRepository specificationRepository;
}
