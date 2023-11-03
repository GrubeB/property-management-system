package pl.app.property.property.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.organization.service.OrganizationQueryService;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.persistence.PropertyRepository;
import pl.app.property.reservation_payment_policy.application.port.in.CreateReservationPaymentPolicyCommand;
import pl.app.property.reservation_payment_policy.application.port.in.CreateReservationPaymentPolicyUseCase;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository repository;
    private final OrganizationQueryService organizationQueryService;

    private final CreateReservationPaymentPolicyUseCase createReservationPaymentPolicyUseCase;

    @Override
    public void settingParentBeforeSave(UUID parentId, PropertyEntity property) {
        OrganizationEntity organization = organizationQueryService.fetchById(parentId);
        property.setOrganization(organization);
    }

    @Override
    public void afterSave(PropertyEntity savedEntity) {
        createReservationPaymentPolicyUseCase.createReservationPaymentPolicy(new CreateReservationPaymentPolicyCommand(savedEntity.getPropertyId()));
    }
}
