package pl.app.property.accommodation_type.service;

import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeAvailabilityCommand;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeAvailabilityUseCase;
import pl.app.property.accommodation_price.application.port.in.CreateAccommodationTypePriceCommand;
import pl.app.property.accommodation_price.application.port.in.CreateAccommodationTypePriceUseCase;
import pl.app.property.accommodation_type.dto.AccommodationTypeCreateDto;
import pl.app.property.accommodation_type.mapper.AccommodationTypeMapper;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.persistence.AccommodationTypeRepository;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
@Getter
class AccommodationTypeServiceImpl implements
        AccommodationTypeService {
    private final AccommodationTypeRepository repository;
    private final PropertyQueryService propertyQueryService;
    private final AccommodationTypeMapper mapper;

    private final CreateAccommodationTypePriceUseCase createAccommodationTypePriceUseCase;
    private final CreateAccommodationTypeAvailabilityUseCase createAccommodationTypeAvailabilityUseCase;

    public AccommodationTypeServiceImpl(AccommodationTypeRepository accommodationRepository,
                                        PropertyQueryService propertyQueryService,
                                        AccommodationTypeMapper mapper,
                                        @Lazy CreateAccommodationTypePriceUseCase createAccommodationTypePriceUseCase,
                                        @Lazy CreateAccommodationTypeAvailabilityUseCase createAccommodationTypeAvailabilityUseCase) {
        this.repository = accommodationRepository;
        this.propertyQueryService = propertyQueryService;
        this.mapper = mapper;
        this.createAccommodationTypePriceUseCase = createAccommodationTypePriceUseCase;
        this.createAccommodationTypeAvailabilityUseCase = createAccommodationTypeAvailabilityUseCase;
    }

    @Override
    public void settingParentBeforeSave(UUID parentId, AccommodationTypeEntity accommodationTypeEntity) {
        PropertyEntity property = propertyQueryService.fetchById(parentId);
        accommodationTypeEntity.setProperty(property);
    }

    @Override
    public void afterDtoSave(AccommodationTypeCreateDto accommodationTypeCreateDto, AccommodationTypeEntity savedEntity) {
        createAccommodationTypePrice(savedEntity.getAccommodationTypeId(), accommodationTypeCreateDto.getDefaultPricePerDay());
        createAccommodationTypeAvailability(savedEntity);
    }


    private void createAccommodationTypeAvailability(AccommodationTypeEntity accommodationTypeEntity) {
        CreateAccommodationTypeAvailabilityCommand createAccommodationTypeAvailabilityCommand = new CreateAccommodationTypeAvailabilityCommand(
                accommodationTypeEntity.getAccommodationTypeId());
        createAccommodationTypeAvailabilityUseCase.create(createAccommodationTypeAvailabilityCommand);
    }

    private void createAccommodationTypePrice(UUID accommodationTypeId, BigDecimal defaultPricePerDay) {
        CreateAccommodationTypePriceCommand command = new CreateAccommodationTypePriceCommand(
                accommodationTypeId,
                defaultPricePerDay);
        createAccommodationTypePriceUseCase.createAccommodationTypePrice(command);
    }
}
