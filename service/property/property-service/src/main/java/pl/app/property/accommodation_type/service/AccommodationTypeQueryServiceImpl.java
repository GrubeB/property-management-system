package pl.app.property.accommodation_type.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_type.persistence.AccommodationTypeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationTypeQueryServiceImpl implements
        AccommodationTypeQueryService {
    private final AccommodationTypeRepository repository;
    private final AccommodationTypeRepository specificationRepository;
}
