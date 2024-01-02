package pl.app.property.user.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.reservation_payment_policy.adapter.out.query.dto.ReservationPaymentPolicyDto;
import pl.app.property.user.adapter.out.persistence.repository.UserRepository;
import pl.app.property.user.adapter.out.query.dto.UserDto;
import pl.app.property.user.adapter.out.query.mapper.UserQueryMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository repository;
    private final UserRepository specificationRepository;
    private final UserQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("UserDto", UserDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
