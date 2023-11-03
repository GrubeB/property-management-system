package pl.app.property.accommodation_availability.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.adapter.out.persistence.repository.AccommodationTypeAvailabilityEntityRepository;
import pl.app.property.accommodation_availability.application.domain.exception.AccommodationAvailabilityException;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Getter
class AccommodationTypeAvailabilityQueryServiceImpl implements
        AccommodationTypeAvailabilityQueryService {
    private final AccommodationTypeAvailabilityEntityRepository repository;
    private final AccommodationTypeAvailabilityEntityRepository specificationRepository;

    @Override
    public AccommodationTypeAvailabilityEntity fetchByAccommodationTypeId(UUID accommodationTypeId) {
        return repository.findByAccommodationType_AccommodationTypeId(accommodationTypeId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

}
