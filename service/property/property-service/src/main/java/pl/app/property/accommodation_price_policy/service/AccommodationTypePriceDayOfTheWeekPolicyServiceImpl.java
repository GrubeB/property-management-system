package pl.app.property.accommodation_price_policy.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_price_policy.exception.AccommodationTypePricePolicyException;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyEntity;
import pl.app.property.accommodation_price_policy.persistence.AccommodationTypePriceDayOfTheWeekPolicyRepository;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class AccommodationTypePriceDayOfTheWeekPolicyServiceImpl implements AccommodationTypePriceDayOfTheWeekPolicyService {
    private final AccommodationTypePriceDayOfTheWeekPolicyRepository repository;
    private final PropertyQueryService propertyQueryService;

    @Override
    public void settingParentBeforeSave(UUID parentId, AccommodationTypePriceDayOfTheWeekPolicyEntity entity) {
        PropertyEntity property = propertyQueryService.fetchById(parentId);
        entity.setProperty(property);
    }

    @Override
    public void beforeSave(AccommodationTypePriceDayOfTheWeekPolicyEntity accommodationTypePriceDayOfTheWeekPolicyEntity) {
        Optional<AccommodationTypePriceDayOfTheWeekPolicyEntity> byPropertyId = repository
                .findByPropertyId(accommodationTypePriceDayOfTheWeekPolicyEntity.getProperty().getPropertyId());
        if (byPropertyId.isPresent()) {
            throw new AccommodationTypePricePolicyException.AccommodationTypePriceDayOfTheWeekPolicyAlreadyExistsException();
        }
    }
}
