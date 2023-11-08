package pl.app.property.amenity.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.amenity.adapter.out.persistence.model.AmenityCategoryEntity;
import pl.app.property.amenity.adapter.out.persistence.model.AmenityEntity;
import pl.app.property.amenity.adapter.out.persistence.model.OrganizationAmenityEntity;
import pl.app.property.amenity.adapter.out.persistence.model.PropertyAmenityEntity;
import pl.app.property.amenity.adapter.out.persistence.repository.AmenityCategoryEntityRepository;
import pl.app.property.amenity.application.domain.exception.AmenityException;
import pl.app.property.amenity.application.domain.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AmenityMapper {
    private final AmenityCategoryEntityRepository amenityCategoryEntityRepository;

    public AmenityEntity mapToAmenityEntity(Amenity domain) {
        return AmenityEntity.builder()
                .amenityId(domain.getAmenityId())
                .name(domain.getName())
                .amenityCategory(this.mapToAmenityCategoryEntity(domain.getCategory()))
                .description(domain.getDescription())
                .standard(domain.getStandard())
                .build();
    }
    public Amenity mapToAmenity(AmenityEntity entity) {
        return new Amenity(
                entity.getAmenityId(),
                entity.getName(),
                entity.getAmenityCategory().getName(),
                entity.getDescription(),
                entity.getStandard()
        );
    }

    public AmenityCategoryEntity mapToAmenityCategoryEntity(String category) {
        return amenityCategoryEntityRepository.findByName(category)
                .orElseThrow(() -> AmenityException.NotFoundAmenitiesCategoryException.fromCategoryName(category));
    }

    public OrganizationHasAmenity mapToOrganizationHasAmenity(OrganizationAmenityEntity entity) {
        AmenityEntity amenityEntity = entity.getAmenity();
        return new OrganizationHasAmenity(this.mapToAmenity(amenityEntity), entity.getActive());
    }

    public OrganizationAmenities mapToOrganizationAmenities(UUID organizationId, Collection<OrganizationAmenityEntity> entities) {
        return new OrganizationAmenities(organizationId, entities.stream().map(this::mapToOrganizationHasAmenity).collect(Collectors.toList()));
    }

    public Set<OrganizationAmenityEntity> mapToOrganizationAmenityEntity(OrganizationAmenities domain) {
        return domain.getOrganizationHasAmenities().stream().map(organizationHasAmenity ->
                new OrganizationAmenityEntity(organizationHasAmenity.getOrganizationAmenityId(),
                        domain.getOrganizationId(),
                        organizationHasAmenity.getActive(),
                        this.mapToAmenityEntity(organizationHasAmenity.getAmenity()))
        ).collect(Collectors.toSet());
    }


    public PropertyHasAmenity mapToPropertyHasAmenity(PropertyAmenityEntity entity) {
        AmenityEntity amenityEntity = entity.getAmenity();
        return new PropertyHasAmenity(this.mapToAmenity(amenityEntity));
    }

    public PropertyAmenities mapToPropertyAmenities(UUID propertyId, Collection<PropertyAmenityEntity> entities) {
        return new PropertyAmenities(propertyId, entities.stream().map(this::mapToPropertyHasAmenity).collect(Collectors.toList()));
    }

    public Set<PropertyAmenityEntity> mapToPropertyAmenityEntity(PropertyAmenities domain) {
        return domain.getPropertyHasAmenities().stream().map(organizationHasAmenity ->
                new PropertyAmenityEntity(organizationHasAmenity.getPropertyAmenityId(),
                        domain.getPropertyId(),
                        this.mapToAmenityEntity(organizationHasAmenity.getAmenity()))
        ).collect(Collectors.toSet());
    }
}
