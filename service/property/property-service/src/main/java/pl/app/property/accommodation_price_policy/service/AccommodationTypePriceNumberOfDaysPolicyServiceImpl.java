package pl.app.property.accommodation_price_policy.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_price_policy.exception.AccommodationTypePricePolicyException;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;
import pl.app.property.accommodation_price_policy.persistence.AccommodationTypePriceNumberOfDaysPolicyRepository;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class AccommodationTypePriceNumberOfDaysPolicyServiceImpl implements AccommodationTypePriceNumberOfDaysPolicyService {
    private final AccommodationTypePriceNumberOfDaysPolicyRepository repository;
    private final PropertyQueryService propertyQueryService;

    @Override
    public void settingParentBeforeSave(UUID parentId, AccommodationTypePriceNumberOfDaysPolicyEntity entity) {
        PropertyEntity property = propertyQueryService.fetchById(parentId);
        entity.setProperty(property);
    }

    @Override
    public void beforeSave(AccommodationTypePriceNumberOfDaysPolicyEntity accommodationTypePriceNumberOfDaysPolicyEntity) {
        Optional<AccommodationTypePriceNumberOfDaysPolicyEntity> byPropertyId = repository
                .findByPropertyId(accommodationTypePriceNumberOfDaysPolicyEntity.getProperty().getPropertyId());
        if (byPropertyId.isPresent()) {
            throw new AccommodationTypePricePolicyException.AccommodationTypePriceNumberOfDaysPolicyAlreadyExistsException();
        }
    }
}
