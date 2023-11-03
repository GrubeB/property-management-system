package pl.app.property.accommodation_price_policy.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_price_policy.exception.AccommodationTypePricePolicyException;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;
import pl.app.property.accommodation_price_policy.persistence.AccommodationTypePriceNumberOfDaysPolicyRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationTypePriceNumberOfDaysPolicyQueryServiceImpl implements AccommodationTypePriceNumberOfDaysPolicyQueryService {
    private final AccommodationTypePriceNumberOfDaysPolicyRepository repository;
    private final AccommodationTypePriceNumberOfDaysPolicyRepository specificationRepository;

    @Override
    public AccommodationTypePriceNumberOfDaysPolicyEntity fetchByPropertyIdId(UUID propertyId) {
        return repository.findByPropertyId(propertyId)
                .orElseThrow(AccommodationTypePricePolicyException.NotFoundAccommodationTypePriceNumberOfDaysPolicyException::new);
    }
}
