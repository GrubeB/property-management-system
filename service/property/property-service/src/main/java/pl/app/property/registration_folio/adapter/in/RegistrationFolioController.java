package pl.app.property.registration_folio.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.registration_folio.application.port.in.*;

import java.util.UUID;

@RestController
@RequestMapping(RegistrationFolioController.resourcePath)
@RequiredArgsConstructor
public class RegistrationFolioController {
    public static final String resourceName = "registration-folios";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    public static final String createRegistrationFolioPath = "create-registration-folio";
    public static final String addChargePath = "add-charge-to-party";
    public static final String startPaymentProcessPath = "start-payment-process";
    public static final String addPaymentPath = "add-payment-to-party";
    public static final String isPartyFolioPaidPath = "is-party-folio-paid";
    public static final String isRegistrationFolioPaidPath = "is-registration-folio-paid";

    public final CreateRegistrationFolioUseCase createRegistrationFolioUseCase;
    public final AddChargeToPartyFolioUseCase addChargeToPartyFolioUseCase;
    public final AddPaymentToPartyFolioUseCase addPaymentToPartyFolioUseCase;
    public final IsPartyFolioPaidUseCase isPartyFolioPaidUseCase;
    public final IsRegistrationFolioPaidUseCase isRegistrationFolioPaidUseCase;
    public final StartRegistrationPaymentProcessUseCase startRegistrationPaymentProcessUseCase;

    @PostMapping(path = createRegistrationFolioPath)
    public ResponseEntity<UUID> createRegistrationFolio(@RequestBody CreateRegistrationFolioCommand command, HttpServletRequest request) {
        UUID registrationFolioId = createRegistrationFolioUseCase.createRegistrationFolio(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(registrationFolioId, request.getRequestURI()))
                .body(registrationFolioId);
    }

    @PostMapping(path = addChargePath)
    public ResponseEntity<Void> addCharge(@RequestBody AddChargeToPartyFolioCommand command) {
        addChargeToPartyFolioUseCase.addCharge(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = startPaymentProcessPath)
    public ResponseEntity<Void> startPaymentProcess(@RequestBody StartRegistrationPaymentProcessCommand command) {
        startRegistrationPaymentProcessUseCase.startPaymentProcess(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = addPaymentPath)
    public ResponseEntity<Void> addPayment(@RequestBody AddPaymentToPartyFolioCommand command) {
        addPaymentToPartyFolioUseCase.addPayment(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = isPartyFolioPaidPath)
    public ResponseEntity<Boolean> isPartyFolioPaid(@RequestBody IsPartyFolioPaidCommand command) {
        Boolean partyFolioPaid = isPartyFolioPaidUseCase.isPartyFolioPaid(command);
        return ResponseEntity.ok(partyFolioPaid);
    }

    @PostMapping(path = isRegistrationFolioPaidPath)
    public ResponseEntity<Boolean> isRegistrationFolioPaid(@RequestBody IsRegistrationFolioPaidCommand command) {
        Boolean registrationFolioPaid = isRegistrationFolioPaidUseCase.isRegistrationFolioPaid(command);
        return ResponseEntity.ok(registrationFolioPaid);
    }
}
