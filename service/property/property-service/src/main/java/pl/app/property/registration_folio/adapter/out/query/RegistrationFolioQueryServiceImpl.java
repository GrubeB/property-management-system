package pl.app.property.registration_folio.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.registration.adapter.out.query.dto.RegistrationDto;
import pl.app.property.registration_folio.adapter.out.persistence.repository.RegistrationFolioRepository;
import pl.app.property.registration_folio.adapter.out.query.dto.RegistrationFolioDto;
import pl.app.property.registration_folio.adapter.out.query.mapper.RegistrationFolioQueryMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class RegistrationFolioQueryServiceImpl implements
        RegistrationFolioQueryService {
    private final RegistrationFolioRepository repository;
    private final RegistrationFolioRepository specificationRepository;
    private final RegistrationFolioQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("RegistrationFolioDto", RegistrationFolioDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
