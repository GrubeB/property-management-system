package pl.app.property.amenity.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.amenity.application.domain.exception.AmenityException;
import pl.app.property.amenity.application.domain.model.Amenity;
import pl.app.property.amenity.application.domain.model.OrganizationAmenities;
import pl.app.property.amenity.application.domain.model.PropertyAmenities;
import pl.app.property.amenity.application.port.in.*;
import pl.app.property.amenity.application.port.out.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
class AmenityService implements
        ActivateAmenityUseCase,
        DeactivateAmenityUseCase,
        CreateAmenityUseCase,
        AddStandardAmenitiesToOrganizationUseCase,
        DeleteAmenityUseCase,
        AddAmenityToPropertyUseCase,
        RemoveAmenityFromPropertyUseCase {

    private final SaveAmenityPort saveAmenityPort;
    private final RemoveAmenityPort removeAmenityPort;
    private final LoadAmenityPort loadAmenityPort;
    private final LoadAllStandardAmenitiesPort loadAllStandardAmenitiesPort;
    private final SaveOrganizationAmenitiesPort saveOrganizationAmenitiesPort;
    private final LoadOrganizationAmenitiesPort loadOrganizationAmenitiesPort;
    private final SavePropertyAmenitiesPort savePropertyAmenitiesPort;
    private final LoadPropertyAmenitiesPort loadPropertyAmenitiesPort;
    private final LoadCategoryPort loadCategoryPort;

    @Override
    public UUID create(CreateAmenityCommand command) {
        Set<String> categories = loadCategoryPort.loadAllCategories();
        if (categories.stream().noneMatch(category -> category.equals(command.getCategory()))) {
            throw AmenityException.NotFoundAmenitiesCategoryException.fromCategoryName(command.getCategory());
        }
        Amenity amenity = new Amenity(command.getName(), command.getCategory(), command.getDescription(), false);
        OrganizationAmenities organizationAmenities = loadOrganizationAmenitiesPort.loadOrganizationAmenities(command.getOrganizationId());
        organizationAmenities.addAmenity(amenity);
        saveAmenityPort.save(amenity);
        saveOrganizationAmenitiesPort.save(organizationAmenities);
        return amenity.getAmenityId();
    }

    @Override
    public void delete(DeleteAmenityCommand command) {
        removeAmenityPort.delete(command.getAmenityId());
    }

    @Override
    public void activate(ActivateAmenityCommand command) {
        OrganizationAmenities organizationAmenities = loadOrganizationAmenitiesPort.loadOrganizationAmenities(command.getOrganizationId());
        organizationAmenities.activateAmenityById(command.getAmenityId());
        saveOrganizationAmenitiesPort.save(organizationAmenities);
    }

    @Override
    public void deactivate(DeactivateAmenityCommand command) {
        OrganizationAmenities organizationAmenities = loadOrganizationAmenitiesPort.loadOrganizationAmenities(command.getOrganizationId());
        organizationAmenities.deactivateAmenityById(command.getAmenityId());
        saveOrganizationAmenitiesPort.save(organizationAmenities);
    }

    @Override
    public void addStandardAmenities(AddStandardAmenitiesToOrganizationCommand command) {
        OrganizationAmenities organizationAmenities = loadOrganizationAmenitiesPort.loadOrganizationAmenities(command.getOrganizationId());
        List<Amenity> amenities = loadAllStandardAmenitiesPort.loadAllStandardAmenities();
        amenities.forEach(organizationAmenities::addAmenity);
        saveOrganizationAmenitiesPort.save(organizationAmenities);
    }

    @Override
    public void addAmenityToProperty(AddAmenityToPropertyCommand command) {
        PropertyAmenities propertyAmenities = loadPropertyAmenitiesPort.loadPropertyAmenities(command.getPropertyId());
        Amenity amenity = loadAmenityPort.loadAmenity(command.getAmenityId());
        propertyAmenities.addAmenity(amenity);
        savePropertyAmenitiesPort.save(propertyAmenities);
    }

    @Override
    public void removeAmenityFromProperty(RemoveAmenityFromPropertyCommand command) {
        PropertyAmenities propertyAmenities = loadPropertyAmenitiesPort.loadPropertyAmenities(command.getPropertyId());
        Amenity amenity = loadAmenityPort.loadAmenity(command.getAmenityId());
        propertyAmenities.removeAmenity(amenity);
        savePropertyAmenitiesPort.save(propertyAmenities);
    }
}
