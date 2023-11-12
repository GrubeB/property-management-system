package pl.app.property.payment.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.payment.adapter.out.persistence.repository.PaymentRepository;
import pl.app.property.payment.adapter.out.query.dto.PaymentDto;
import pl.app.property.payment.adapter.out.query.mapper.PaymentQueryMapper;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class PaymentQueryServiceImpl implements PaymentQueryService {
    private final PaymentRepository repository;
    private final PaymentRepository specificationRepository;
    private final PaymentQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = Map.of(
            "PaymentDto", PaymentDto.class,
            "BaseDto", BaseDto.class
    );
}
