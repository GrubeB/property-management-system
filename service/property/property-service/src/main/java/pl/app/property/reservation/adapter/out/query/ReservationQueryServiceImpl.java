package pl.app.property.reservation.adapter.out.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationEntity;
import pl.app.property.reservation.adapter.out.persistence.repository.ReservationRepository;
import pl.app.property.reservation.adapter.out.query.dto.ReservationDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class ReservationQueryServiceImpl implements ReservationQueryService {
    private final ReservationRepository repository;
    private final ReservationRepository specificationRepository;

    private final Map<String, Class<?>> dtoClasses = Map.of(
            "BaseDto", BaseDto.class,
            "ReservationDto", ReservationDto.class
    );
    public final Class<ReservationDto> defaultDtoClass = ReservationDto.class;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> dtoMappers = new HashMap<>();
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        dtoMappers.put(new AbstractMap.SimpleEntry<>(ReservationEntity.class, BaseDto.class),
                (ReservationEntity e) -> modelMapper.map(e, BaseDto.class));
        dtoMappers.put(new AbstractMap.SimpleEntry<>(ReservationEntity.class, ReservationDto.class),
                (ReservationEntity e) -> modelMapper.map(e, ReservationDto.class));
    }
}
