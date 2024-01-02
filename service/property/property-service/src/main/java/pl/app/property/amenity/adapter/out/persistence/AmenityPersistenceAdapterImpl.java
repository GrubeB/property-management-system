package pl.app.property.amenity.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.amenity.adapter.out.persistence.mapper.AmenityMapper;
import pl.app.property.amenity.adapter.out.persistence.model.AmenityCategoryEntity;
import pl.app.property.amenity.adapter.out.persistence.model.AmenityEntity;
import pl.app.property.amenity.adapter.out.persistence.model.OrganizationAmenityEntity;
import pl.app.property.amenity.adapter.out.persistence.model.PropertyAmenityEntity;
import pl.app.property.amenity.adapter.out.persistence.repository.AmenityCategoryEntityRepository;
import pl.app.property.amenity.adapter.out.persistence.repository.AmenityEntityRepository;
import pl.app.property.amenity.adapter.out.persistence.repository.OrganizationAmenityEntityRepository;
import pl.app.property.amenity.adapter.out.persistence.repository.PropertyAmenityEntityRepository;
import pl.app.property.amenity.application.domain.exception.AmenityException;
import pl.app.property.amenity.application.domain.model.Amenity;
import pl.app.property.amenity.application.domain.model.OrganizationAmenities;
import pl.app.property.amenity.application.domain.model.PropertyAmenities;
import pl.app.property.amenity.application.port.out.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
class AmenityPersistenceAdapterImpl implements
        LoadAllStandardAmenitiesPort,
        LoadAmenityPort,
        LoadCategoryPort,
        LoadOrganizationAmenitiesPort,
        LoadPropertyAmenitiesPort,
        RemoveAmenityPort,
        SaveAmenityPort,
        SaveOrganizationAmenitiesPort,
        SavePropertyAmenitiesPort {
    private final AmenityCategoryEntityRepository amenityCategoryEntityRepository;
    private final AmenityEntityRepository amenityEntityRepository;
    private final OrganizationAmenityEntityRepository organizationAmenityEntityRepository;
    private final PropertyAmenityEntityRepository propertyAmenityEntityRepository;
    private final AmenityMapper amenityMapper;

    @Override
    @Transactional(readOnly = true)
    public Set<String> loadAllCategories() {
        return amenityCategoryEntityRepository.findAll()
                .stream().map(AmenityCategoryEntity::getName)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void delete(UUID amenityId) {
        amenityEntityRepository.deleteById(amenityId);
    }

    @Override
    public Amenity save(Amenity amenity) {
        AmenityEntity amenityEntity = amenityMapper.mapToAmenityEntity(amenity);
        AmenityEntity savedAmenityMapper = amenityEntityRepository.save(amenityEntity);
        return amenityMapper.mapToAmenity(savedAmenityMapper);
    }

    @Override
    public OrganizationAmenities save(OrganizationAmenities organizationAmenities) {
        Set<OrganizationAmenityEntity> collect = amenityMapper.mapToOrganizationAmenityEntity(organizationAmenities);
        List<OrganizationAmenityEntity> organizationAmenityEntities = organizationAmenityEntityRepository.saveAll(collect);
        return amenityMapper.mapToOrganizationAmenities(organizationAmenities.getOrganizationId(), organizationAmenityEntities);
    }

    @Override
    public PropertyAmenities save(PropertyAmenities propertyAmenities) {
        Set<PropertyAmenityEntity> collect = amenityMapper.mapToPropertyAmenityEntity(propertyAmenities);
        List<PropertyAmenityEntity> propertyAmenityEntities = propertyAmenityEntityRepository.saveAll(collect);
        return amenityMapper.mapToPropertyAmenities(propertyAmenities.getPropertyId(), propertyAmenityEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Amenity> loadAllStandardAmenities() {
        List<AmenityEntity> allStandardAmenities = amenityEntityRepository.findByStandardTrue();
        return allStandardAmenities.stream().map(amenityMapper::mapToAmenity).toList();
    }


    @Override
    public OrganizationAmenities loadOrganizationAmenities(UUID organizationId) {
        Set<OrganizationAmenityEntity> byOrganizationId = organizationAmenityEntityRepository.findByOrganizationId(organizationId);
        return amenityMapper.mapToOrganizationAmenities(organizationId, byOrganizationId);
    }

    @Override
    public PropertyAmenities loadPropertyAmenities(UUID propertyId) {
        Set<PropertyAmenityEntity> byPropertyId = propertyAmenityEntityRepository.findByPropertyId(propertyId);
        return amenityMapper.mapToPropertyAmenities(propertyId, byPropertyId);
    }

    @Override
    public Amenity loadAmenity(UUID amenityId) {
        AmenityEntity amenityEntity = amenityEntityRepository.findById(amenityId)
                .orElseThrow(() -> AmenityException.NotFoundAmenityException.fromId(amenityId));
        return amenityMapper.mapToAmenity(amenityEntity);
    }
}
