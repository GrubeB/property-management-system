package pl.app.property.registration_folio.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationEntity;
import pl.app.property.registration.adapter.out.persistence.repository.RegistrationRepository;
import pl.app.property.registration.application.domain.exception.RegistrationException;
import pl.app.property.registration_folio.adapter.out.persistence.model.PartyFolioChargeEntity;
import pl.app.property.registration_folio.adapter.out.persistence.model.PartyFolioEntity;
import pl.app.property.registration_folio.adapter.out.persistence.model.PartyFolioPaymentEntity;
import pl.app.property.registration_folio.adapter.out.persistence.model.RegistrationFolioEntity;
import pl.app.property.registration_folio.application.domain.model.RegistrationFolio;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolio;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioCharge;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioPayment;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegistrationFolioMapper {
    private final RegistrationRepository registrationRepository;

    public RegistrationFolioEntity mapToRegistrationFolioEntity(RegistrationFolio domain) {
        RegistrationFolioEntity entity = RegistrationFolioEntity.builder()
                .registrationFolioId(domain.getRegistrationFolioId())
                .registration(this.mapToRegistrationEntity(domain.getRegistrationId()))
                .partyFolios(domain.getPartyFolios().stream().map(this::mapToPartyFolioEntity).collect(Collectors.toSet()))
                .build();
        entity.getPartyFolios().forEach(p -> p.setRegistrationFolio(entity));
        return entity;
    }

    private RegistrationEntity mapToRegistrationEntity(UUID registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(() -> RegistrationException.NotFoundRegistrationException.fromId(registrationId));
    }

    private PartyFolioEntity mapToPartyFolioEntity(RegistrationPartyFolio domain) {
        PartyFolioEntity entity = PartyFolioEntity.builder()
                .partyFolioId(domain.getPartyFolioId())
                .partyId(domain.getPartyId())
                .payments(domain.getPayments().stream().map(this::mapToPartyFolioPaymentEntity).collect(Collectors.toSet()))
                .charges(domain.getCharges().stream().map(this::mapToPartyFolioChargeEntity).collect(Collectors.toSet()))
                .build();
        entity.getPayments().forEach(p -> p.setPartyFolio(entity));
        entity.getCharges().forEach(p -> p.setPartyFolio(entity));
        return entity;
    }

    private PartyFolioChargeEntity mapToPartyFolioChargeEntity(RegistrationPartyFolioCharge domain) {
        return PartyFolioChargeEntity.builder()
                .chargeId(domain.getChargeId())
                .type(domain.getType())
                .name(domain.getName())
                .amount(domain.getAmount())
                .current(domain.getCurrent())
                .date(domain.getDate())
                .build();
    }

    private PartyFolioPaymentEntity mapToPartyFolioPaymentEntity(RegistrationPartyFolioPayment domain) {
        return PartyFolioPaymentEntity.builder()
                .paymentId(domain.getPaymentId())
                .guestId(domain.getGuestId())
                .amount(domain.getAmount())
                .current(domain.getCurrent())
                .date(domain.getDate())
                .build();
    }


    public RegistrationFolio mapToRegistrationFolio(RegistrationFolioEntity entity) {
        return new RegistrationFolio(
                entity.getRegistrationFolioId(),
                entity.getRegistration().getRegistrationId(),
                entity.getRegistration().getProperty().getPropertyId(),
                entity.getPartyFolios().stream().map(this::mapToPartyFolio).collect(Collectors.toList())
        );
    }

    private RegistrationPartyFolio mapToPartyFolio(PartyFolioEntity entity) {
        return new RegistrationPartyFolio(
                entity.getPartyFolioId(),
                entity.getPartyId(),
                entity.getPayments().stream().map(this::mapToPartyFolioPayment).collect(Collectors.toList()),
                entity.getCharges().stream().map(this::mapToPartyFolioCharge).collect(Collectors.toList())
        );
    }

    private RegistrationPartyFolioCharge mapToPartyFolioCharge(PartyFolioChargeEntity entity) {
        return new RegistrationPartyFolioCharge(
                entity.getChargeId(),
                entity.getType(),
                entity.getName(),
                entity.getAmount(),
                entity.getCurrent(),
                entity.getDate()
        );
    }

    private RegistrationPartyFolioPayment mapToPartyFolioPayment(PartyFolioPaymentEntity entity) {
        return new RegistrationPartyFolioPayment(
                entity.getPaymentId(),
                entity.getGuestId(),
                entity.getAmount(),
                entity.getCurrent(),
                entity.getDate()
        );
    }

}
