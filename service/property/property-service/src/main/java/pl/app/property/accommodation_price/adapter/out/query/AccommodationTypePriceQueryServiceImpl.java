package pl.app.property.accommodation_price.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePriceEntity;
import pl.app.property.accommodation_price.adapter.out.persistence.repository.AccommodationTypePriceEntityRepository;
import pl.app.property.accommodation_price.application.domain.exception.AccommodationTypePriceException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationTypePriceQueryServiceImpl implements AccommodationTypePriceQueryService {

    private final AccommodationTypePriceEntityRepository repository;
    private final AccommodationTypePriceEntityRepository specificationRepository;

    @Override
    public AccommodationTypePriceEntity fetchByAccommodationTypeId(UUID accommodationTypeId) {
        return repository.findByAccommodationType_AccommodationTypeId(accommodationTypeId)
                .orElseThrow(AccommodationTypePriceException.NotFoundAccommodationTypePriceException::new);
    }

}
