package pl.app.property.accommodation_price.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_price.adapter.out.persistence.mapper.AccommodationTypePriceMapper;
import pl.app.property.accommodation_price.adapter.out.persistence.mapper.AccommodationTypePricePolicyMapper;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePriceEntity;
import pl.app.property.accommodation_price.adapter.out.persistence.model.AccommodationTypePricePolicyEntity;
import pl.app.property.accommodation_price.adapter.out.persistence.repository.AccommodationTypePriceEntityRepository;
import pl.app.property.accommodation_price.adapter.out.persistence.repository.AccommodationTypePricePolicyEntityRepository;
import pl.app.property.accommodation_price.application.domain.exception.AccommodationTypePriceException;
import pl.app.property.accommodation_price.application.domain.model.AccommodationTypePrice;
import pl.app.property.accommodation_price.application.domain.model.Policy;
import pl.app.property.accommodation_price.application.domain.model.PropertyPolicies;
import pl.app.property.accommodation_price.application.port.out.*;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyEntity;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceNumberOfDaysPolicyEntity;
import pl.app.property.accommodation_price_policy.service.AccommodationTypePriceDayOfTheWeekPolicyQueryService;
import pl.app.property.accommodation_price_policy.service.AccommodationTypePriceNumberOfDaysPolicyQueryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccommodationTypePricePersistenceAdapter implements
        LoadPropertyPoliciesPort,
        SavePropertyPoliciesPort,
        LoadAccommodationTypePricePolicyPort,
        LoadAccommodationTypePricePort,
        SaveAccommodationTypePricePort {
    private final AccommodationTypePriceEntityRepository accommodationTypePriceEntityRepository;
    private final AccommodationTypePricePolicyEntityRepository accommodationTypePricePolicyEntityRepository;
    private final AccommodationTypePriceMapper accommodationTypePriceMapper;
    private final AccommodationTypePricePolicyMapper accommodationTypePricePolicyMapper;
    private final AccommodationTypePriceDayOfTheWeekPolicyQueryService accommodationTypePriceDayOfTheWeekPolicyService;
    private final AccommodationTypePriceNumberOfDaysPolicyQueryService accommodationTypePriceNumberOfDaysPolicyService;

    @Override
    public AccommodationTypePrice loadAccommodationTypePriceByAccommodationTypeId(UUID accommodationTypeId) {
        AccommodationTypePriceEntity entity = accommodationTypePriceEntityRepository.findByAccommodationType_AccommodationTypeId(accommodationTypeId)
                .orElseThrow(() -> AccommodationTypePriceException.NotFoundAccommodationTypePriceException.fromId(accommodationTypeId));
        List<AccommodationTypePricePolicyEntity> policyEntities = accommodationTypePricePolicyEntityRepository
                .findByPropertyId(entity.getAccommodationType().getProperty().getPropertyId());
        return accommodationTypePriceMapper.mapToAccommodationTypePrice(entity, policyEntities);
    }

    @Override
    public UUID saveAccommodationTypePrice(AccommodationTypePrice domain) {
        AccommodationTypePriceEntity entity = accommodationTypePriceMapper.mapToAccommodationTypePriceEntity(domain);
        AccommodationTypePriceEntity savedEntity = accommodationTypePriceEntityRepository.saveAndFlush(entity);
        return savedEntity.getAccommodationTypePriceId();
    }

    @Override
    public AccommodationTypePriceNumberOfDaysPolicyEntity loadAccommodationTypePriceNumberOfDaysPolicyEntityByPropertyId(UUID propertyId) {
        return accommodationTypePriceNumberOfDaysPolicyService.fetchByPropertyIdId(propertyId);
    }

    @Override
    public AccommodationTypePriceDayOfTheWeekPolicyEntity loadAccommodationTypePriceDayOfTheWeekPolicyEntityByPropertyId(UUID propertyId) {
        return accommodationTypePriceDayOfTheWeekPolicyService.fetchByPropertyId(propertyId);
    }

    @Override
    public PropertyPolicies loadPropertyPoliciesByPropertyId(UUID propertyId) {
        List<AccommodationTypePricePolicyEntity> policyEntities = accommodationTypePricePolicyEntityRepository.findByPropertyId(propertyId);
        List<Policy> policies = policyEntities.stream().map(accommodationTypePricePolicyMapper::mapToPolicy).collect(Collectors.toList());
        return new PropertyPolicies(propertyId, policies);
    }

    @Override
    public void savePropertyPoliciesByPropertyId(PropertyPolicies propertyPolicies) {
        List<AccommodationTypePricePolicyEntity> policyEntities = propertyPolicies.getPolicies().stream()
                .map(accommodationTypePricePolicyMapper::mapToAccommodationTypePricePolicyEntity)
                .collect(Collectors.toList());
        policyEntities.forEach(policy -> policy.setPropertyId(propertyPolicies.getPropertyId()));
        accommodationTypePricePolicyEntityRepository.saveAllAndFlush(policyEntities);
    }
}
