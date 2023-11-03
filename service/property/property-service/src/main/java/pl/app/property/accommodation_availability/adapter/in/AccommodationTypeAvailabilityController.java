package pl.app.property.accommodation_availability.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.property.accommodation_availability.application.port.in.*;

@RestController
@RequestMapping(AccommodationTypeAvailabilityController.resourcePath)
@RequiredArgsConstructor
public class AccommodationTypeAvailabilityController {

    public static final String resourceName = "accommodation-type-availabilities";
    public static final String resourcePath = "/api/v1/accommodation-types/{accommodationTypeId}/" + resourceName;

    private final CreateAccommodationReservationUseCase createAccommodationReservationUseCase;
    private final CreateAccommodationTypeReservationUseCase createAccommodationTypeReservationUseCase;
    private final ChangeAccommodationReservationStatusUseCase changeAccommodationReservationStatusUseCase;
    private final ManualAssignAccommodationTypeReservationUseCase manualAssignAccommodationTypeReservationUseCase;
    private final AutomaticAssignAccommodationTypeReservationUseCase automaticAssignAccommodationTypeReservationUseCase;
    private final UnassignAccommodationTypeReservationUseCase unassignAccommodationTypeReservationUseCase;
    private final IsAccommodationAvailableUseCase isAccommodationAvailableUseCase;
    private final IsAccommodationTypeAvailableUseCase isAccommodationTypeAvailableUseCase;
    private final RemoveAccommodationReservationUseCase removeAccommodationReservationUseCase;
    private final RemoveAccommodationTypeReservationUseCase removeAccommodationTypeReservationUseCase;
    public static final String isAccommodationAvailablePath = "/accommodation-availability";
    public static final String isAccommodationTypeAvailablePath = "/accommodation-type-availability";
    public static final String createAccommodationReservationPath = "/create-reservation";
    public static final String createAccommodationTypeReservationPath = "/create-type-reservation";
    public static final String removeAccommodationReservationPath = "/remove-reservation";
    public static final String removeAccommodationTypeReservationPath = "/remove-type-reservation";
    public static final String changeAccommodationReservationStatusPath = "/change-reservation-status";
    public static final String manualAssignPath = "/manual-assign";
    public static final String automaticAssignPath = "/automatic-assign";
    public static final String unassignPath = "/unassign";

    @PostMapping(path = isAccommodationAvailablePath)
    public ResponseEntity<Boolean> isAccommodationAvailable(@RequestBody IsAccommodationAvailableCommand command) {
        return ResponseEntity
                .ok(isAccommodationAvailableUseCase.isAccommodationAvailable(command));
    }

    @PostMapping(path = isAccommodationTypeAvailablePath)
    public ResponseEntity<Boolean> isAccommodationTypeAvailable(@RequestBody IsAccommodationTypeAvailableCommand command) {
        return ResponseEntity
                .ok(isAccommodationTypeAvailableUseCase.isAccommodationTypeAvailable(command));
    }

    @PostMapping(path = createAccommodationReservationPath)
    public ResponseEntity<Void> createAccommodationReservation(@RequestBody CreateAccommodationReservationCommand command) {
        createAccommodationReservationUseCase.createAccommodationReservation(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = createAccommodationTypeReservationPath)
    public ResponseEntity<Void> createAccommodationTypeReservation(@RequestBody CreateAccommodationTypeReservationCommand command) {
        createAccommodationTypeReservationUseCase.createAccommodationTypeReservation(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(path = removeAccommodationReservationPath)
    public ResponseEntity<Void> removeAccommodationReservation(@RequestBody RemoveAccommodationReservationCommand command) {
        removeAccommodationReservationUseCase.removeAccommodationReservation(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(path = removeAccommodationTypeReservationPath)
    public ResponseEntity<Void> removeAccommodationTypeReservation(@RequestBody RemoveAccommodationTypeReservationCommand command) {
        removeAccommodationTypeReservationUseCase.removeAccommodationTypeReservation(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = changeAccommodationReservationStatusPath)
    public ResponseEntity<Void> changeAccommodationReservationStatus(@RequestBody ChangeAccommodationReservationStatusCommand command) {
        changeAccommodationReservationStatusUseCase.changeAccommodationReservationStatus(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = manualAssignPath)
    public ResponseEntity<Void> manualAssign(@RequestBody ManualAssignAccommodationTypeReservationCommand command) {
        manualAssignAccommodationTypeReservationUseCase.manualAssign(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = automaticAssignPath)
    public ResponseEntity<Void> automaticAssign(@RequestBody AutomaticAssignAccommodationTypeReservationCommand command) {
        automaticAssignAccommodationTypeReservationUseCase.automaticAssign(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = unassignPath)
    public ResponseEntity<Void> unassign(@RequestBody UnassignAccommodationTypeReservationCommand command) {
        unassignAccommodationTypeReservationUseCase.unassign(command);
        return ResponseEntity
                .noContent()
                .build();
    }
}
