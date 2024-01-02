package pl.app.property.reservation_folio.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.reservation.adapter.out.query.dto.ReservationDto;
import pl.app.property.reservation_folio.adapter.out.persistence.repository.ReservationFolioRepository;
import pl.app.property.reservation_folio.adapter.out.query.dto.ReservationFolioDto;
import pl.app.property.reservation_folio.adapter.out.query.mapper.ReservationFolioQueryMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class ReservationFolioQueryServiceImpl implements ReservationFolioQueryService {
    private final ReservationFolioRepository repository;
    private final ReservationFolioRepository specificationRepository;
    private final ReservationFolioQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("ReservationFolioDto", ReservationFolioDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
