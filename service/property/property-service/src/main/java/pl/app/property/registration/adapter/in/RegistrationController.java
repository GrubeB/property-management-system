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

    public final CreateRegistrationUseCase createRegistrationUseCase;
    public final CancelRegistrationUseCase cancelRegistrationUseCase;
    public final FinishRegistrationUseCase finishRegistrationUseCase;
    public final ConfirmRegistrationUseCase confirmRegistrationUseCase;

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
}
