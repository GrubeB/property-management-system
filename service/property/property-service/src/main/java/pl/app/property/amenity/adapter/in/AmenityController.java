package pl.app.property.amenity.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.amenity.application.port.in.*;

import java.util.UUID;

@RestController
@RequestMapping(AmenityController.resourcePath)
@RequiredArgsConstructor
public class AmenityController {
    public static final String resourceName = "amenities";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/" + resourceName;

    public static final String createPath = "/create-amenity";
    public static final String deletePath = "/delete-amenity";
    public static final String activatePath = "/activate-amenity";
    public static final String deactivatePath = "/deactivate-amenity";
    public static final String addAmenityToPropertyPath = "/add-amenity-to-property";
    public static final String removeAmenityFromPropertyPath = "/remove-amenity-from-property";
    public static final String addStandardAmenitiesPath = "/add-standard-amenities";

    public final CreateAmenityUseCase createAmenityUseCase;
    public final ActivateAmenityUseCase activateAmenityUseCase;
    public final DeactivateAmenityUseCase deactivateAmenityUseCase;
    public final DeleteAmenityUseCase deleteAmenityUseCase;
    public final AddAmenityToPropertyUseCase addAmenityToPropertyUseCase;
    public final RemoveAmenityFromPropertyUseCase removeAmenityFromPropertyUseCase;
    public final AddStandardAmenitiesToOrganizationUseCase addStandardAmenitiesToOrganizationUseCase;

    @PostMapping(path = createPath)
    public ResponseEntity<String> create(@RequestBody CreateAmenityCommand command, HttpServletRequest request) {
        UUID amenityId = createAmenityUseCase.create(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(amenityId, request.getRequestURI()))
                .body(amenityId.toString());
    }

    @DeleteMapping(path = deletePath)
    public ResponseEntity<Void> delete(@RequestBody DeleteAmenityCommand command) {
        deleteAmenityUseCase.delete(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = activatePath)
    public ResponseEntity<Void> activate(@RequestBody ActivateAmenityCommand command) {
        activateAmenityUseCase.activate(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = deactivatePath)
    public ResponseEntity<Void> deactivate(@RequestBody DeactivateAmenityCommand command) {
        deactivateAmenityUseCase.deactivate(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = addAmenityToPropertyPath)
    public ResponseEntity<Void> addAmenityToProperty(@RequestBody AddAmenityToPropertyCommand command) {
        addAmenityToPropertyUseCase.addAmenityToProperty(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = removeAmenityFromPropertyPath)
    public ResponseEntity<Void> removeAmenityFromProperty(@RequestBody RemoveAmenityFromPropertyCommand command) {
        removeAmenityFromPropertyUseCase.removeAmenityFromProperty(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = addStandardAmenitiesPath)
    public ResponseEntity<Void> addStandardAmenities(@RequestBody AddStandardAmenitiesToOrganizationCommand command) {
        addStandardAmenitiesToOrganizationUseCase.addStandardAmenities(command);
        return ResponseEntity
                .noContent()
                .build();
    }
}
