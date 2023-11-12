package pl.app.property.reservation_payment_policy.adapter.out.query.mapper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.core.service.mapper.Mapper;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.model.ReservationPaymentPolicyEntity;
import pl.app.property.reservation_payment_policy.adapter.out.query.dto.ReservationPaymentPolicyDto;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Component
@RequiredArgsConstructor
public class ReservationPaymentPolicyQueryMapper implements Mapper {
    private final ModelMapper modelMapper;

    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();

    @PostConstruct
    void init() {
        addMapper(ReservationPaymentPolicyEntity.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(ReservationPaymentPolicyEntity.class, ReservationPaymentPolicyDto.class, e -> modelMapper.map(e, ReservationPaymentPolicyDto.class));
    }
}
