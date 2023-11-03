package pl.app.property.accommodation_price_policy.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_price_policy.exception.AccommodationTypePricePolicyException;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyEntity;
import pl.app.property.accommodation_price_policy.persistence.AccommodationTypePriceDayOfTheWeekPolicyRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class AccommodationTypePriceDayOfTheWeekPolicyQueryServiceImpl implements AccommodationTypePriceDayOfTheWeekPolicyQueryService {
    private final AccommodationTypePriceDayOfTheWeekPolicyRepository repository;
    private final AccommodationTypePriceDayOfTheWeekPolicyRepository specificationRepository;

    @Override
    public AccommodationTypePriceDayOfTheWeekPolicyEntity fetchByPropertyId(UUID propertyId) {
        return repository.findByPropertyId(propertyId)
                .orElseThrow(AccommodationTypePricePolicyException.NotFoundAccommodationTypePriceDayOfTheWeekPolicyException::new);
    }
}
