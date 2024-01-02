package pl.app.property.organization.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.guest.dto.GuestDto;
import pl.app.property.organization.dto.OrganizationDto;
import pl.app.property.organization.mapper.OrganizationMapper;
import pl.app.property.organization.persistence.OrganizationRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class OrganizationQueryServiceImpl implements
        OrganizationQueryService {
    private final OrganizationRepository repository;
    private final OrganizationRepository specificationRepository;
    private final OrganizationMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("OrganizationDto", OrganizationDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
