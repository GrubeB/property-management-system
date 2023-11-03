package pl.app.property.reservation_payment_policy.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.reservation_payment_policy.application.port.in.*;

import java.util.UUID;

@RestController
@RequestMapping(ReservationPaymentPolicyController.resourcePath)
@RequiredArgsConstructor
public class ReservationPaymentPolicyController {
    public static final String resourceName = "reservation-payment-policies";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    public static final String createReservationPaymentPolicyPath = "/create-reservation-payment-policy";
    public static final String changeNumberOfDaysPath = "/change-number-of-days";
    public static final String changeStatusToFirstDayPath = "/change-status-to-first-day";
    public static final String changeStatusToFullPath = "/change-status-to-full";
    public static final String changeStatusToNonePath = "/change-status-to-none";

    public final CreateReservationPaymentPolicyUseCase createReservationPaymentPolicyUseCase;
    public final ChangeReservationPaymentPolicyNumberOfDaysBeforeRegistrationUseCase changeReservationPaymentPolicyNumberOfDaysBeforeRegistrationUseCase;
    public final ChangeReservationPaymentPolicyStatusToFirstDayUseCase changeReservationPaymentPolicyStatusToFirstDayUseCase;
    public final ChangeReservationPaymentPolicyStatusToFullUseCase changeReservationPaymentPolicyStatusToFullUseCase;
    public final ChangeReservationPaymentPolicyStatusToNoneUseCase changeReservationPaymentPolicyStatusToNoneUseCase;

    @PostMapping(path = createReservationPaymentPolicyPath)
    public ResponseEntity<UUID> createReservationPaymentPolicy(@RequestBody CreateReservationPaymentPolicyCommand command, HttpServletRequest request) {
        UUID reservationPaymentPolicyId = createReservationPaymentPolicyUseCase.createReservationPaymentPolicy(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(reservationPaymentPolicyId, request.getRequestURI()))
                .body(reservationPaymentPolicyId);
    }

    @PostMapping(path = changeNumberOfDaysPath)
    public ResponseEntity<Void> changeNumberOfDays(@RequestBody ChangeReservationPaymentPolicyNumberOfDaysBeforeRegistrationCommand command) {
        changeReservationPaymentPolicyNumberOfDaysBeforeRegistrationUseCase.changeNumberOfDays(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = changeStatusToFirstDayPath)
    public ResponseEntity<Void> changeStatusToFirstDay(@RequestBody ChangeReservationPaymentPolicyStatusToFirstDayCommand command) {
        changeReservationPaymentPolicyStatusToFirstDayUseCase.changeStatusToFirstDay(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = changeStatusToFullPath)
    public ResponseEntity<Void> changeStatusToFull(@RequestBody ChangeReservationPaymentPolicyStatusToFullCommand command) {
        changeReservationPaymentPolicyStatusToFullUseCase.changeStatusToFull(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = changeStatusToNonePath)
    public ResponseEntity<Void> changeStatusToNone(@RequestBody ChangeReservationPaymentPolicyStatusToNoneCommand command) {
        changeReservationPaymentPolicyStatusToNoneUseCase.changeStatusToNone(command);
        return ResponseEntity
                .noContent()
                .build();
    }
}
