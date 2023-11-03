package pl.app.property.accommodation.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.accommodation.application.port.in.AddAccommodationCommand;
import pl.app.property.accommodation.application.port.in.AddAccommodationUseCase;
import pl.app.property.accommodation.application.port.in.RemoveAccommodationCommand;
import pl.app.property.accommodation.application.port.in.RemoveAccommodationUseCase;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationController.resourcePath)
@RequiredArgsConstructor
public class AccommodationController {
    public static final String resourceName = "accommodations";
    public static final String resourcePath = "/api/v1/accommodation-types/{accommodationTypeId}/" + resourceName;
    public static final String addAccommodationPath = "/add-accommodation";
    public static final String removeAccommodationPath = "/remove-accommodation";

    public final AddAccommodationUseCase addAccommodationUseCase;
    public final RemoveAccommodationUseCase removeAccommodationUseCase;

    @PostMapping(path = addAccommodationPath)
    public ResponseEntity<String> addAccommodation(@RequestBody AddAccommodationCommand command, HttpServletRequest request) {
        UUID accommodationId = addAccommodationUseCase.addAccommodation(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(accommodationId, request.getRequestURI()))
                .body(accommodationId.toString());
    }

    @PostMapping(path = removeAccommodationPath)
    public ResponseEntity<Void> removeAccommodation(@RequestBody RemoveAccommodationCommand command) {
        removeAccommodationUseCase.removeAccommodation(command);
        return ResponseEntity
                .noContent()
                .build();
    }

}
