package pl.app.property.accommodation.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation.adapter.out.persistence.repository.AccommodationRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationQueryServiceImpl implements AccommodationQueryService {
    private final AccommodationRepository repository;
    private final AccommodationRepository specificationRepository;
}
