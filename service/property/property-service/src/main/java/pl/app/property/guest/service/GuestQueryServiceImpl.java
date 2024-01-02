package pl.app.property.guest.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.web.dto.BaseDto;
import pl.app.property.amenity.adapter.out.query.dto.PropertyAmenityDto;
import pl.app.property.guest.dto.GuestDto;
import pl.app.property.guest.mapper.GuestMapper;
import pl.app.property.guest.persistence.GuestRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class GuestQueryServiceImpl implements GuestQueryService {
    private final GuestRepository repository;
    private final GuestRepository specificationRepository;
    private final GuestMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("GuestDto", GuestDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
