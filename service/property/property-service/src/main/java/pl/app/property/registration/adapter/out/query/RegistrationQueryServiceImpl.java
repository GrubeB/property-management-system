package pl.app.property.registration.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.payment.adapter.out.query.dto.PaymentDto;
import pl.app.property.registration.adapter.out.persistence.repository.RegistrationRepository;
import pl.app.property.registration.adapter.out.query.dto.RegistrationDto;
import pl.app.property.registration.adapter.out.query.mapper.RegistrationQueryMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class RegistrationQueryServiceImpl implements RegistrationQueryService {
    private final RegistrationRepository repository;
    private final RegistrationRepository specificationRepository;
    private final RegistrationQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("RegistrationDto", RegistrationDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
