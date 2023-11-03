package pl.app.property.amenity.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.amenity.adapter.out.persistence.repository.OrganizationAmenityEntityRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class OrganizationAmenityQueryServiceImpl implements OrganizationAmenityQueryService {
    private final OrganizationAmenityEntityRepository repository;
    private final OrganizationAmenityEntityRepository specificationRepository;
}
