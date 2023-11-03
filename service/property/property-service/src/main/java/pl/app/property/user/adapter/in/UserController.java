package pl.app.property.user.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.user.application.port.in.*;

import java.util.UUID;

@RestController
@RequestMapping(UserController.resourcePath)
@RequiredArgsConstructor
public class UserController {
    public static final String resourceName = "users";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/" + resourceName;
    public static final String createUserPath = "";
    public static final String addPrivilegePath = "/add-privilege";
    public static final String removePrivilegePath = "/remove-privilege";

    public final CreateUserUseCase createReservationFolio;
    public final AddPrivilegeUseCase addPrivilegeUseCase;
    public final RemovePrivilegeUseCase removePrivilegeUseCase;

    @PostMapping(path = createUserPath)
    public ResponseEntity<String> createUser(@RequestBody CreateUserCommand command, HttpServletRequest request) {
        UUID userId = createReservationFolio.createUser(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(userId, request.getRequestURI()))
                .body(userId.toString());
    }

    @PostMapping(path = addPrivilegePath)
    public ResponseEntity<Void> addPrivilege(@RequestBody AddPrivilegeCommand command) {
        addPrivilegeUseCase.addPrivilege(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = removePrivilegePath)
    public ResponseEntity<Void> removePrivilege(@RequestBody RemovePrivilegeCommand command) {
        removePrivilegeUseCase.removePrivilege(command);
        return ResponseEntity
                .noContent()
                .build();
    }
}
