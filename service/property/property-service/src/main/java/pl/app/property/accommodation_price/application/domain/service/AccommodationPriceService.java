package pl.app.property.accommodation_price.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.accommodation_price.application.domain.model.AccommodationTypePrice;
import pl.app.property.accommodation_price.application.domain.policy.AccommodationTypePricePolicy;
import pl.app.property.accommodation_price.application.domain.policy.AccommodationTypePricePolicyType;
import pl.app.property.accommodation_price.application.port.in.*;
import pl.app.property.accommodation_price.application.port.out.LoadAccommodationTypePricePort;
import pl.app.property.accommodation_price.application.port.out.SaveAccommodationTypePricePort;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
class AccommodationPriceService implements
        CreateAccommodationTypePriceUseCase,
        GenerateAccommodationTypePriceUseCase,
        CalculateAccommodationTypePriceUseCase {
    private final Map<AccommodationTypePricePolicyType, AccommodationTypePricePolicy> accommodationTypePricePolicies;
    private final LoadAccommodationTypePricePort loadAccommodationTypePricePort;
    private final SaveAccommodationTypePricePort saveAccommodationTypePricePort;

    @Override
    public void createAccommodationTypePrice(CreateAccommodationTypePriceCommand command) {
        AccommodationTypePrice accommodationTypePrice = new AccommodationTypePrice(command.getAccommodationType(), command.getDefaultPricePerDay());
        saveAccommodationTypePricePort.saveAccommodationTypePrice(accommodationTypePrice);
    }

    @Override
    public BigDecimal calculateAccommodationTypePrice(CalculateAccommodationTypePriceCommand command) {
        AccommodationTypePrice accommodationTypePrice = loadAccommodationTypePricePort.loadAccommodationTypePriceByAccommodationTypeId(command.getAccommodationTypeId());
        return accommodationTypePrice.gerPriceForDateRange(command.getStartDate(), command.getEndDate(), accommodationTypePricePolicies);
    }

    @Override
    public void generateAccommodationTypePrice(GenerateAccommodationTypePriceCommand command) {
        AccommodationTypePrice accommodationTypePrice = loadAccommodationTypePricePort.loadAccommodationTypePriceByAccommodationTypeId(command.getAccommodationTypeId());
        accommodationTypePrice.generatePricesFromRange(command.getFromDate(), command.getToDate(), accommodationTypePricePolicies);
        saveAccommodationTypePricePort.saveAccommodationTypePrice(accommodationTypePrice);
    }

}
