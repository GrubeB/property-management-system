package pl.app.property.accommodation_type.service;

import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeAvailabilityCommand;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeAvailabilityUseCase;
import pl.app.property.accommodation_price.application.port.in.CreateAccommodationTypePriceCommand;
import pl.app.property.accommodation_price.application.port.in.CreateAccommodationTypePriceUseCase;
import pl.app.property.accommodation_type.command.CreateAccommodationTypeCommand;
import pl.app.property.accommodation_type.model.AccommodationTypeBedEntity;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.model.AccommodationTypeImageEntity;
import pl.app.property.accommodation_type.persistence.AccommodationTypeRepository;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Getter
class AccommodationTypeServiceImpl implements
        AccommodationTypeService {
    private final AccommodationTypeRepository repository;
    private final PropertyQueryService propertyQueryService;

    private final CreateAccommodationTypePriceUseCase createAccommodationTypePriceUseCase;
    private final CreateAccommodationTypeAvailabilityUseCase createAccommodationTypeAvailabilityUseCase;

    public AccommodationTypeServiceImpl(@Lazy AccommodationTypeRepository accommodationRepository,
                                        @Lazy PropertyQueryService propertyQueryService,
                                        @Lazy CreateAccommodationTypePriceUseCase createAccommodationTypePriceUseCase,
                                        @Lazy CreateAccommodationTypeAvailabilityUseCase createAccommodationTypeAvailabilityUseCase) {
        this.repository = accommodationRepository;
        this.propertyQueryService = propertyQueryService;
        this.createAccommodationTypePriceUseCase = createAccommodationTypePriceUseCase;
        this.createAccommodationTypeAvailabilityUseCase = createAccommodationTypeAvailabilityUseCase;
    }

    @Override
    public void settingParentBeforeSave(UUID parentId, AccommodationTypeEntity accommodationTypeEntity) {
        PropertyEntity property = propertyQueryService.fetchById(parentId);
        accommodationTypeEntity.setProperty(property);
    }

    @Override
    public AccommodationTypeEntity create(CreateAccommodationTypeCommand command) {
        AccommodationTypeEntity newAccommodationTypeEntity = mapToAccommodationTypeEntity(command);
        AccommodationTypeEntity savedAccommodationTypeEntity = this.create(command.getPropertyId(), newAccommodationTypeEntity);
        createAccommodationTypePrice(savedAccommodationTypeEntity.getAccommodationTypeId(), command.getDefaultPricePerDay());
        createAccommodationTypeAvailability(savedAccommodationTypeEntity);
        return savedAccommodationTypeEntity;
    }

    private AccommodationTypeEntity mapToAccommodationTypeEntity(CreateAccommodationTypeCommand command) {
        AccommodationTypeEntity entity = AccommodationTypeEntity.builder()
                .name(command.getName())
                .abbreviation(command.getAbbreviation())
                .description(command.getDescription())
                .genderRoomType(command.getGenderRoomType())
                .roomType(command.getRoomType())
                .beds(command.getBeds().stream()
                        .map(b -> AccommodationTypeBedEntity.builder()
                                .numberOfBeds(b.getNumberOfBeds())
                                .type(b.getType())
                                .build())
                        .collect(Collectors.toSet()))
                .images(command.getImages().stream()
                        .map(i -> AccommodationTypeImageEntity.builder()
                                .fileId(i.getFileId())
                                .description(i.getDescription())
                                .build())
                        .collect(Collectors.toSet())
                )
                .build();
        entity.getBeds().forEach(b -> b.setAccommodationType(entity));
        return entity;
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
