package pl.app.property.amenity.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.amenity.adapter.out.persistence.repository.AmenityEntityRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AmenityQueryServiceImpl implements AmenityQueryService {
    private final AmenityEntityRepository repository;
    private final AmenityEntityRepository specificationRepository;
}
