package pl.app.property.reservation_folio.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.reservation_folio.application.port.in.*;

import java.util.UUID;

@RestController
@RequestMapping(ReservationFolioController.resourcePath)
@RequiredArgsConstructor
public class ReservationFolioController {
    public static final String resourceName = "reservation-folios";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    public static final String createReservationFolioPath = "create-reservation-folio";
    public static final String startPaymentProcessPath = "start-payment-process";
    public static final String addChargePath = "add-charge";
    public static final String addPaymentPath = "add-payment";
    public static final String isReservationFolioPaidPath = "is-reservation-paid";
    public static final String removeChargePath = "remove-charge";

    public final CreateReservationFolioUseCase createReservationFolioUseCase;
    public final AddChargeFolioUseCase addChargeFolioUseCase;
    public final AddPaymentUseCase addPaymentUseCase;
    public final IsReservationFolioPaidUseCase isReservationFolioPaidUseCase;
    public final RemoveChargeUseCase removeChargeUseCase;
    public final StartReservationPaymentProcessUseCase startReservationPaymentProcessUseCase;

    @PostMapping(path = createReservationFolioPath)
    public ResponseEntity<UUID> createReservationFolio(@RequestBody CreateReservationFolioCommand command, HttpServletRequest request) {
        UUID reservationFolioId = createReservationFolioUseCase.createReservationFolio(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(reservationFolioId, request.getRequestURI()))
                .body(reservationFolioId);
    }

    @PostMapping(path = startPaymentProcessPath)
    public ResponseEntity<Void> startPaymentProcess(@RequestBody StartReservationPaymentProcessCommand command) {
        startReservationPaymentProcessUseCase.startPaymentProcess(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = addChargePath)
    public ResponseEntity<Void> addCharge(@RequestBody AddChargeCommand command) {
        addChargeFolioUseCase.addCharge(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = addPaymentPath)
    public ResponseEntity<Void> addPayment(@RequestBody AddPaymentCommand command) {
        addPaymentUseCase.addPayment(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = isReservationFolioPaidPath)
    public ResponseEntity<Boolean> isReservationFolioPaid(@RequestBody IsReservationFolioPaidCommand command) {
        return ResponseEntity
                .ok(isReservationFolioPaidUseCase.isReservationFolioPaid(command));
    }

    @PostMapping(path = removeChargePath)
    public ResponseEntity<Void> removeCharge(@RequestBody RemoveChargeCommand command) {
        removeChargeUseCase.removeCharge(command);
        return ResponseEntity
                .noContent()
                .build();
    }
}
