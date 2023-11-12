package pl.app.property.reservation.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.reservation.adapter.out.persistence.repository.ReservationRepository;
import pl.app.property.reservation.adapter.out.query.dto.ReservationDto;
import pl.app.property.reservation.adapter.out.query.mapper.ReservationQueryMapper;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class ReservationQueryServiceImpl implements ReservationQueryService {
    private final ReservationRepository repository;
    private final ReservationRepository specificationRepository;
    private final ReservationQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = Map.of(
            "ReservationDto", ReservationDto.class,
            "BaseDto", BaseDto.class
    );
}
