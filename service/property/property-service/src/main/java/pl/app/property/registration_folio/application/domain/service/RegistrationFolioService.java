package pl.app.property.registration_folio.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.registration_folio.application.domain.model.RegistrationFolio;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolio;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioCharge;
import pl.app.property.registration_folio.application.domain.model.RegistrationPartyFolioPayment;
import pl.app.property.registration_folio.application.port.in.*;
import pl.app.property.registration_folio.application.port.out.LoadRegistrationFolioPort;
import pl.app.property.registration_folio.application.port.out.PaymentPort;
import pl.app.property.registration_folio.application.port.out.RegistrationMailPort;
import pl.app.property.registration_folio.application.port.out.SaveRegistrationFolioPort;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class RegistrationFolioService implements
        isPartyFolioEmptyUseCase,
        StartRegistrationPaymentProcessUseCase,
        IsPartyFolioPaidUseCase,
        IsRegistrationFolioPaidUseCase,
        AddChargeToPartyFolioUseCase,
        RemoveChargeFromPartyFolioUseCase,
        AddPaymentToPartyFolioUseCase,
        CreateRegistrationFolioWithExistingChargesUseCase,
        CreateRegistrationFolioUseCase {

    private final LoadRegistrationFolioPort loadRegistrationFolioPort;
    private final SaveRegistrationFolioPort saveRegistrationFolioPort;
    private final PaymentPort paymentPort;
    private final RegistrationMailPort registrationMailPort;

    @Override
    public UUID createRegistrationFolio(CreateRegistrationFolioCommand command) {
        RegistrationFolio registrationFolio = new RegistrationFolio(command.getRegistrationId(), command.getPartyIds());
        return saveRegistrationFolioPort.saveRegistrationFolio(registrationFolio);
    }

    @Override
    public UUID createRegistrationFolio(CreateRegistrationFolioWithExistingChargesCommand command) {
        RegistrationFolio registrationFolio = new RegistrationFolio();
        command.getPartyFolios().forEach(pf -> {
            RegistrationPartyFolio partyFolio = new RegistrationPartyFolio(pf.getPartyId());
            pf.getCharges().forEach(ch -> partyFolio.addChargeToParty(ch.getType(), ch.getName(), ch.getAmount(), ch.getCurrent(), ch.getDate()));
            pf.getPayments().forEach(p -> partyFolio.addPaymentToParty(p.getGuestId(), p.getAmount(), p.getCurrent(), p.getDate()));
            registrationFolio.addParty(partyFolio);
        });
        return saveRegistrationFolioPort.saveRegistrationFolio(registrationFolio);
    }

    @Override
    public UUID addCharge(AddChargeToPartyFolioCommand command) {
        RegistrationFolio registrationFolio = loadRegistrationFolioPort.loadRegistrationFolio(command.getRegistrationFolioId());
        RegistrationPartyFolioCharge newCharge = registrationFolio.addChargeToPartyFolio(command.getPartyId(), command.getType(), command.getName(), command.getAmount(), command.getCurrent());
        saveRegistrationFolioPort.saveRegistrationFolio(registrationFolio);
        return newCharge.getChargeId();
    }

    @Override
    public void removeCharge(RemoveChargeFromPartyFolioCommand command) {
        RegistrationFolio registrationFolio = loadRegistrationFolioPort.loadRegistrationFolio(command.getRegistrationFolioId());
        registrationFolio.removeChargeFromPartyFolio(command.getChargeId());
        saveRegistrationFolioPort.saveRegistrationFolio(registrationFolio);
    }

    @Override
    public UUID addPayment(AddPaymentToPartyFolioCommand command) {
        RegistrationFolio registrationFolio = loadRegistrationFolioPort.loadRegistrationFolioByPartyFolio(command.getPartyFolioId());
        RegistrationPartyFolioPayment newPayment = registrationFolio.addNewPaymentToPartyFolio(command.getPartyFolioId(),
                command.getGuestId(), command.getAmount(), command.getCurrent());
        saveRegistrationFolioPort.saveRegistrationFolio(registrationFolio);
        return newPayment.getPaymentId();
    }

    @Override
    public Boolean isPartyFolioPaid(IsPartyFolioPaidCommand command) {
        RegistrationFolio registrationFolio = loadRegistrationFolioPort.loadRegistrationFolio(command.getRegistrationFolioId());
        return registrationFolio.isPartyFolioPaid(command.getPartyId());
    }

    @Override
    public Boolean isRegistrationFolioPaid(IsRegistrationFolioPaidCommand command) {
        RegistrationFolio registrationFolio = loadRegistrationFolioPort.loadRegistrationFolio(command.getRegistrationFolioId());
        return registrationFolio.isRegistrationFolioPaid();
    }

    @Override
    public boolean isPartyFolioEmpty(IsPartyFolioEmptyCommand command) {
        RegistrationFolio registrationFolio = loadRegistrationFolioPort.loadRegistrationFolio(command.getRegistrationFolioId());
        return registrationFolio.isPartyFolioEmpty(command.getPartyId());
    }

    @Override
    public UUID startPaymentProcess(StartRegistrationPaymentProcessCommand command) {
        RegistrationFolio registrationFolio = loadRegistrationFolioPort.loadRegistrationFolioByPartyFolio(command.getPartyFolioId());
        UUID paymentId = paymentPort.createPayment(registrationFolio.getPropertyId(), command.getPartyFolioId(),
                "Registration: " + registrationFolio.getRegistrationId(), command.getGuestId(), command.getAmount(), command.getCurrent());
        registrationMailPort.sendMail(paymentId, command.getGuestId(), registrationFolio.getPropertyId());
        return paymentId;
    }

}
