package pl.app.property.reservation_payment_policy.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.repositroy.ReservationPaymentPolicyRepository;
import pl.app.property.reservation_payment_policy.adapter.out.query.dto.ReservationPaymentPolicyDto;
import pl.app.property.reservation_payment_policy.adapter.out.query.mapper.ReservationPaymentPolicyQueryMapper;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class ReservationPaymentPolicyQueryServiceImpl implements ReservationPaymentPolicyQueryService {
    private final ReservationPaymentPolicyRepository repository;
    private final ReservationPaymentPolicyRepository specificationRepository;
    private final ReservationPaymentPolicyQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = Map.of(
            "ReservationPaymentPolicyDto", ReservationPaymentPolicyDto.class,
            "BaseDto", BaseDto.class
    );
}
