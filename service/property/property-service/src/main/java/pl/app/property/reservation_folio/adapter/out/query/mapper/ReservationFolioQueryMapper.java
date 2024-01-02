package pl.app.property.reservation_folio.adapter.out.query.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioEntity;
import pl.app.property.reservation_folio.adapter.out.query.dto.ReservationFolioDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class ReservationFolioQueryMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(ReservationFolioEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(ReservationFolioEntity.class, ReservationFolioDto.class, e -> modelMapper.map(e, ReservationFolioDto.class));
    }
}
