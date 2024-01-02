package pl.app.property.organization.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.security.authentication.AuthenticationService;
import pl.app.common.shared.authorization.PermissionDomainObjectType;
import pl.app.common.shared.authorization.PermissionName;
import pl.app.property.amenity.application.port.in.AddStandardAmenitiesToOrganizationCommand;
import pl.app.property.amenity.application.port.in.AddStandardAmenitiesToOrganizationUseCase;
import pl.app.property.organization.mapper.OrganizationMapper;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.organization.persistence.OrganizationRepository;
import pl.app.property.user.application.port.in.AddOrganizationToUserCommand;
import pl.app.property.user.application.port.in.AddOrganizationToUserUseCase;
import pl.app.property.user.application.port.in.AddPrivilegeCommand;
import pl.app.property.user.application.port.in.AddPrivilegeUseCase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.app.common.shared.authorization.PermissionName.*;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class OrganizationServiceImpl implements
        OrganizationService {
    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;
    private final AuthenticationService authenticationService;

    private final AddStandardAmenitiesToOrganizationUseCase addStandardAmenitiesToOrganizationUseCase;
    private final AddOrganizationToUserUseCase addOrganizationToUserUseCase;
    private final AddPrivilegeUseCase addPrivilegeUseCase;

    @Override
    public void afterSave(OrganizationEntity savedEntity) {
        UUID organizationId = savedEntity.getId();
        addStandardAmenitiesToOrganizationUseCase.addStandardAmenities(new AddStandardAmenitiesToOrganizationCommand(savedEntity.getOrganizationId()));

        authenticationService.getCurrentUserId()
                .ifPresent(currentUserId -> {
                    addOrganizationToUserUseCase.addOrganizationToUser(new AddOrganizationToUserCommand(currentUserId, organizationId));
                    addPrivilegeUseCase.addPrivilege(new AddPrivilegeCommand(currentUserId, getPermissionNamesAddedToUserOnOrganization().stream()
                            .map(permissionName -> new AddPrivilegeCommand.Privilege(organizationId, PermissionDomainObjectType.ORGANIZATION.name(), permissionName.name()))
                            .collect(Collectors.toList())));
                });
    }
    private List<PermissionName> getPermissionNamesAddedToUserOnOrganization(){
        return List.of(
                ACCOMMODATION_READ,
                ACCOMMODATION_WRITE,
                ACCOMMODATION_AVAILABILITY_READ,
                ACCOMMODATION_AVAILABILITY_WRITE,
                ACCOMMODATION_PRICE_READ,
                ACCOMMODATION_PRICE_WRITE,
                ACCOMMODATION_PRICE_POLICY_READ,
                ACCOMMODATION_PRICE_POLICY_WRITE,
                ACCOMMODATION_TYPE_READ,
                ACCOMMODATION_TYPE_WRITE,
                AMENITY_READ,
                AMENITY_WRITE,
                GUEST_READ,
                GUEST_WRITE,
                ORGANIZATION_READ,
                ORGANIZATION_WRITE,
                PROPERTY_READ,
                PROPERTY_WRITE,
                REGISTRATION_READ,
                REGISTRATION_WRITE,
                REGISTRATION_FOLIO_READ,
                REGISTRATION_FOLIO_WRITE,
                RESERVATION_READ,
                RESERVATION_WRITE,
                RESERVATION_FOLIO_READ,
                RESERVATION_FOLIO_WRITE,
                RESERVATION_PAYMENT_POLICY_READ,
                RESERVATION_PAYMENT_POLICY_WRITE,
                USER_READ,
                USER_WRITE
        );
    }
}
