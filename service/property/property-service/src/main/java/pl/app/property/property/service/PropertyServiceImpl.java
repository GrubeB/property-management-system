package pl.app.property.property.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.security.authentication.AuthenticationService;
import pl.app.common.shared.authorization.PermissionDomainObjectType;
import pl.app.common.shared.authorization.PermissionName;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.organization.service.OrganizationQueryService;
import pl.app.property.property.mapper.PropertyMapper;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.persistence.PropertyRepository;
import pl.app.property.reservation_payment_policy.application.port.in.CreateReservationPaymentPolicyCommand;
import pl.app.property.reservation_payment_policy.application.port.in.CreateReservationPaymentPolicyUseCase;
import pl.app.property.user.application.port.in.AddPrivilegeCommand;
import pl.app.property.user.application.port.in.AddPrivilegeUseCase;
import pl.app.property.user.application.port.in.AddPropertyToUserCommand;
import pl.app.property.user.application.port.in.AddPropertyToUserUseCase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.app.common.shared.authorization.PermissionName.*;
import static pl.app.common.shared.authorization.PermissionName.USER_WRITE;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository repository;
    private final PropertyMapper mapper;
    private final OrganizationQueryService organizationQueryService;
    private final AuthenticationService authenticationService;

    private final CreateReservationPaymentPolicyUseCase createReservationPaymentPolicyUseCase;
    private final AddPropertyToUserUseCase addPropertyToUserUseCase;
    private final AddPrivilegeUseCase addPrivilegeUseCase;

    @Override
    public void settingParentBeforeSave(UUID parentId, PropertyEntity property) {
        OrganizationEntity organization = organizationQueryService.fetchById(parentId);
        property.setOrganization(organization);
    }

    @Override
    public void afterSave(PropertyEntity savedEntity) {
        UUID propertyId = savedEntity.getId();
        createReservationPaymentPolicyUseCase.createReservationPaymentPolicy(new CreateReservationPaymentPolicyCommand(savedEntity.getPropertyId()));
        authenticationService.getCurrentUserId().ifPresent(currentUserId ->{
            addPropertyToUserUseCase.addPropertyToUser(new AddPropertyToUserCommand(currentUserId, propertyId));
            addPrivilegeUseCase.addPrivilege(new AddPrivilegeCommand(currentUserId, getPermissionNamesAddedToUserOnProperty().stream()
                    .map(permissionName -> new AddPrivilegeCommand.Privilege(propertyId, PermissionDomainObjectType.PROPERTY.name(), permissionName.name()))
                    .collect(Collectors.toList())));
        });
    }
    private List<PermissionName> getPermissionNamesAddedToUserOnProperty(){
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
                GUEST_READ,
                GUEST_WRITE,
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
                RESERVATION_PAYMENT_POLICY_WRITE
        );
    }
}
