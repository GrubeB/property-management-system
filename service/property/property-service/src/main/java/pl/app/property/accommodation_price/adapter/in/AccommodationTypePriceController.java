package pl.app.property.accommodation_price.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.property.accommodation_price.application.port.in.CalculateAccommodationTypePriceCommand;
import pl.app.property.accommodation_price.application.port.in.CalculateAccommodationTypePriceUseCase;
import pl.app.property.accommodation_price.application.port.in.GenerateAccommodationTypePriceCommand;
import pl.app.property.accommodation_price.application.port.in.GenerateAccommodationTypePriceUseCase;

import java.math.BigDecimal;

@RestController
@RequestMapping(AccommodationTypePriceController.resourcePath)
@RequiredArgsConstructor
public class AccommodationTypePriceController {

    public static final String resourceName = "accommodation-type-prices";
    public static final String resourcePath = "/api/v1/accommodation-types/{accommodationTypeId}/" + resourceName;
    public static final String calculateAccommodationTypePricePath = "/calculate";
    public static final String generateAccommodationTypePricePath = "/generate";

    private final CalculateAccommodationTypePriceUseCase calculateAccommodationTypePriceUseCase;
    private final GenerateAccommodationTypePriceUseCase generateAccommodationTypePriceUseCase;

    @PostMapping(path = calculateAccommodationTypePricePath)
    public ResponseEntity<BigDecimal> calculateAccommodationTypePrice(@RequestBody CalculateAccommodationTypePriceCommand command) {
        return ResponseEntity
                .ok(calculateAccommodationTypePriceUseCase.calculateAccommodationTypePrice(command));
    }

    @PostMapping(path = generateAccommodationTypePricePath)
    public ResponseEntity<String> generateAccommodationTypePrice(@RequestBody GenerateAccommodationTypePriceCommand command) {
        generateAccommodationTypePriceUseCase.generateAccommodationTypePrice(command);
        return ResponseEntity
                .noContent()
                .build();
    }
}
