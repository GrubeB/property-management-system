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

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AmenityMapper {
    private final AmenityCategoryEntityRepository amenityCategoryEntityRepository;

    public AmenityEntity map(Amenity domain) {
        return AmenityEntity.builder()
                .amenityId(domain.getAmenityId())
                .name(domain.getName())
                .amenityCategoryEntity(this.map(domain.getCategory()))
                .description(domain.getDescription())
                .standard(domain.getStandard())
                .build();
    }

    public Amenity map(AmenityEntity entity) {
        return new Amenity(
                entity.getAmenityId(),
                entity.getName(),
                entity.getAmenityCategoryEntity().getName(),
                entity.getDescription(),
                entity.getStandard()
        );
    }

    public AmenityCategoryEntity map(String category) {
        return amenityCategoryEntityRepository.findByName(category)
                .orElseThrow(() -> AmenityException.NotFoundAmenitiesCategoryException.fromCategoryName(category));
    }

    public OrganizationHasAmenity map(OrganizationAmenityEntity entity) {
        AmenityEntity amenityEntity = entity.getAmenityEntity();
        return new OrganizationHasAmenity(this.map(amenityEntity), entity.getActive());
    }

    public OrganizationAmenities mapToOrganizationAmenities(UUID organizationId, Collection<OrganizationAmenityEntity> entities) {
        return new OrganizationAmenities(organizationId, entities.stream().map(this::map).collect(Collectors.toList()));
    }

    public Set<OrganizationAmenityEntity> map(OrganizationAmenities domain) {
        return domain.getOrganizationHasAmenities().stream().map(organizationHasAmenity ->
                new OrganizationAmenityEntity(organizationHasAmenity.getOrganizationAmenityId(),
                        domain.getOrganizationId(),
                        organizationHasAmenity.getActive(),
                        this.map(organizationHasAmenity.getAmenity()))
        ).collect(Collectors.toSet());
    }


    public PropertyHasAmenity map(PropertyAmenityEntity entity) {
        AmenityEntity amenityEntity = entity.getAmenityEntity();
        return new PropertyHasAmenity(this.map(amenityEntity));
    }

    public PropertyAmenities mapToPropertyAmenities(UUID propertyId, Collection<PropertyAmenityEntity> entities) {
        return new PropertyAmenities(propertyId, entities.stream().map(this::map).collect(Collectors.toList()));
    }

    public Set<PropertyAmenityEntity> map(PropertyAmenities domain) {
        return domain.getPropertyHasAmenities().stream().map(organizationHasAmenity ->
                new PropertyAmenityEntity(organizationHasAmenity.getPropertyAmenityId(),
                        domain.getPropertyId(),
                        this.map(organizationHasAmenity.getAmenity()))
        ).collect(Collectors.toSet());
    }
}
