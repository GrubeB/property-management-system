package pl.app.property.organization.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.amenity.application.port.in.AddStandardAmenitiesToOrganizationCommand;
import pl.app.property.amenity.application.port.in.AddStandardAmenitiesToOrganizationUseCase;
import pl.app.property.organization.mapper.OrganizationMapper;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.organization.persistence.OrganizationRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class OrganizationServiceImpl implements
        OrganizationService {
    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;

    private final AddStandardAmenitiesToOrganizationUseCase addStandardAmenitiesToOrganizationUseCase;

    @Override
    public void afterSave(OrganizationEntity savedEntity) {
        addStandardAmenitiesToOrganizationUseCase.addStandardAmenities(new AddStandardAmenitiesToOrganizationCommand(savedEntity.getOrganizationId()));
    }
}
