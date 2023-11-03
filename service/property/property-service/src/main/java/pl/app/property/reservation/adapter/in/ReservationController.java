package pl.app.property.reservation.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.reservation.application.port.in.*;

import java.util.UUID;

@RestController
@RequestMapping(ReservationController.resourcePath)
@RequiredArgsConstructor
public class ReservationController {
    public static final String resourceName = "reservations";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    public static final String createReservationPath = "create-reservation";
    public static final String cancelReservationPath = "cancel-reservation";
    public static final String confirmedReservationPath = "confirm-reservation";
    public static final String rejectReservationPath = "reject-reservation";
    public static final String finishReservationPath = "finish-reservation";

    public final CancelReservationUseCase cancelReservationUseCase;
    public final ConfirmReservationUseCase confirmReservationUseCase;
    public final CreateReservationUseCase createReservationUseCase;
    public final FinishReservationUseCase finishReservationUseCase;
    public final RejectReservationUseCase rejectReservationUseCase;

    @PostMapping(path = createReservationPath)
    public ResponseEntity<String> createReservation(@RequestBody CreateReservationCommand command, HttpServletRequest request) {
        UUID reservationId = createReservationUseCase.createReservation(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(reservationId, request.getRequestURI()))
                .body(reservationId.toString());
    }

    @PostMapping(path = cancelReservationPath)
    public ResponseEntity<Void> cancelReservation(@RequestBody CancelReservationCommand command) {
        cancelReservationUseCase.cancelReservation(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = confirmedReservationPath)
    public ResponseEntity<Void> confirmedReservation(@RequestBody ConfirmReservationCommand command) {
        confirmReservationUseCase.confirmedReservation(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = rejectReservationPath)
    public ResponseEntity<Void> rejectReservation(@RequestBody RejectReservationCommand command) {
        rejectReservationUseCase.rejectReservation(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = finishReservationPath)
    public ResponseEntity<UUID> finishReservation(@RequestBody FinishReservationCommand command) {
        return ResponseEntity
                .ok(finishReservationUseCase.finishReservation(command));
    }
}
