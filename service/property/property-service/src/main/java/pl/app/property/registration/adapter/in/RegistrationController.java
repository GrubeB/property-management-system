package pl.app.property.registration.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.registration.application.port.in.*;

import java.util.UUID;

@RestController
@RequestMapping(RegistrationController.resourcePath)
@RequiredArgsConstructor
public class RegistrationController {
    public static final String resourceName = "registrations";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    public static final String createRegistrationPath = "create-registration";
    public static final String cancelRegistrationPath = "cancel-registration";
    public static final String finishRegistrationPath = "finish-registration";
    public static final String confirmRegistrationPath = "confirm-registration";
    public static final String addBookingToRegistrationPath = "add-booking";
    public static final String removeBookingFromRegistrationPath = "remove-booking";
    public static final String addGuestToBooking = "add-guest-to-booking";
    public static final String removeGuestFromBookingPath = "remove-guest-from-booking";
    public static final String addGuestToPartyPath = "add-guest-to-party";
    public static final String changeGuestInPartyPath = "change-guest-in-party";
    public static final String removeGuestFromPartyPath = "remove-guest-from-party";
    public static final String addPartyPath = "add-party";
    public static final String removePartyPath = "remove-party";

    public final CreateRegistrationUseCase createRegistrationUseCase;
    public final CancelRegistrationUseCase cancelRegistrationUseCase;
    public final FinishRegistrationUseCase finishRegistrationUseCase;
    public final ConfirmRegistrationUseCase confirmRegistrationUseCase;

    public final AddBookingToRegistrationUseCase addBookingToRegistrationUseCase;
    public final RemoveBookingFromRegistrationUseCase removeBookingFromRegistrationUseCase;

    public final AddGuestToBookingUseCase addGuestToBookingUseCase;
    public final RemoveGuestFromBookingUseCase removeGuestFromBookingUseCase;

    public final AddGuestToPartyUseCase addGuestToPartyUseCase;
    public final ChangeGuestPartyUseCase changeGuestPartyUseCase;
    public final RemoveGuestFromPartyUseCase removeGuestFromPartyUseCase;

    public final AddPartyUseCase addPartyUseCase;
    public final RemovePartyUseCase removePartyUseCase;

    @PostMapping(path = createRegistrationPath)
    public ResponseEntity<String> createRegistration(@RequestBody CreateRegistrationCommand command, HttpServletRequest request) {
        UUID registrationId = createRegistrationUseCase.createRegistration(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(registrationId, request.getRequestURI()))
                .body(registrationId.toString());
    }

    @PostMapping(path = cancelRegistrationPath)
    public ResponseEntity<Void> cancelRegistration(@RequestBody CancelRegistrationCommand command) {
        cancelRegistrationUseCase.cancelRegistration(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = finishRegistrationPath)
    public ResponseEntity<Void> finishRegistration(@RequestBody FinishRegistrationCommand command) {
        finishRegistrationUseCase.finishRegistration(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = confirmRegistrationPath)
    public ResponseEntity<Void> confirmRegistration(@RequestBody ConfirmRegistrationCommand command) {
        confirmRegistrationUseCase.confirmRegistration(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = addBookingToRegistrationPath)
    public ResponseEntity<Void> addBookingToRegistration(@RequestBody AddBookingToRegistrationCommand command) {
        addBookingToRegistrationUseCase.addBookingToRegistration(command);
        return ResponseEntity
                .noContent()
                .build();
    }
    @PostMapping(path = removeBookingFromRegistrationPath)
    public ResponseEntity<Void> removeBookingFromRegistration(@RequestBody RemoveBookingFromRegistrationCommand command) {
        removeBookingFromRegistrationUseCase.removeBookingFromRegistration(command);
        return ResponseEntity
                .noContent()
                .build();
    }
    @PostMapping(path = addGuestToBooking)
    public ResponseEntity<Void> addGuestToBooking(@RequestBody AddGuestToBookingCommand command) {
        addGuestToBookingUseCase.addGuestToBooking(command);
        return ResponseEntity
                .noContent()
                .build();
    }
    @PostMapping(path = removeGuestFromBookingPath)
    public ResponseEntity<Void> removeGuestFromBooking(@RequestBody RemoveGuestFromBookingCommand command) {
        removeGuestFromBookingUseCase.removeGuestFromBooking(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = addGuestToPartyPath)
    public ResponseEntity<Void> addGuestToParty(@RequestBody AddGuestToPartyCommand command) {
        addGuestToPartyUseCase.addGuestToParty(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = changeGuestInPartyPath)
    public ResponseEntity<Void> changeGuestInParty(@RequestBody ChaneGuestPartyCommand command) {
        changeGuestPartyUseCase.changeGuestInParty(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = removeGuestFromPartyPath)
    public ResponseEntity<Void> removeGuestFromParty(@RequestBody RemoveGuestFromPartyCommand command) {
        removeGuestFromPartyUseCase.removeGuestFromParty(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = addPartyPath)
    public ResponseEntity<Void> addParty(@RequestBody AddPartyCommand command) {
        addPartyUseCase.addParty(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = removePartyPath)
    public ResponseEntity<Void> removeParty(@RequestBody RemovePartyCommand command) {
        removePartyUseCase.removeParty(command);
        return ResponseEntity
                .noContent()
                .build();
    }
}
