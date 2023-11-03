package pl.app.property.amenity.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.amenity.adapter.out.persistence.repository.PropertyAmenityEntityRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class PropertyAmenityQueryServiceImpl implements PropertyAmenityQueryService {
    private final PropertyAmenityEntityRepository repository;
    private final PropertyAmenityEntityRepository specificationRepository;
}
