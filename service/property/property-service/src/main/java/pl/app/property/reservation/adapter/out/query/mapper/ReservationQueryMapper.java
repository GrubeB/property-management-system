package pl.app.property.reservation.adapter.out.query.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationEntity;
import pl.app.property.reservation.adapter.out.query.dto.ReservationDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class ReservationQueryMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(ReservationEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(ReservationEntity.class, ReservationDto.class, e -> modelMapper.map(e, ReservationDto.class));
    }
}
