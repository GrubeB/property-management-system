package pl.app.property.accommodation_availability.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationReservation;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeReservation;
import pl.app.property.accommodation_availability.application.domain.policy.AccommodationAssignmentPolicy;
import pl.app.property.accommodation_availability.application.domain.policy.AccommodationTypeAvailabilityPolicy;
import pl.app.property.accommodation_availability.application.port.in.*;
import pl.app.property.accommodation_availability.application.port.out.LoadAccommodationAvailabilityPort;
import pl.app.property.accommodation_availability.application.port.out.LoadAccommodationReservationPort;
import pl.app.property.accommodation_availability.application.port.out.SaveAccommodationAvailabilityPort;
import pl.app.property.accommodation_availability.application.port.out.SaveAccommodationReservationPort;
import pl.app.property.accommodation_type.model.AccommodationTypeEntity;
import pl.app.property.accommodation_type.service.AccommodationTypeQueryService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class AccommodationTypeAvailabilityService implements
        RemoveAccommodationReservationUseCase,
        RemoveAccommodationTypeReservationUseCase,
        IsAccommodationAvailableUseCase,
        IsAccommodationTypeAvailableUseCase,
        UnassignAccommodationTypeReservationUseCase,
        CreateAccommodationTypeAvailabilityUseCase,
        AutomaticAssignAccommodationTypeReservationUseCase,
        ManualAssignAccommodationTypeReservationUseCase,
        CreateAccommodationTypeReservationUseCase,
        ChangeAccommodationReservationStatusUseCase,
        CreateAccommodationReservationUseCase {

    private final AccommodationTypeAvailabilityPolicy accommodationTypeAvailabilityPolicy;
    private final AccommodationAssignmentPolicy accommodationAssignmentPolicy;

    private final LoadAccommodationAvailabilityPort loadAccommodationAvailabilityPort;
    private final SaveAccommodationAvailabilityPort saveAccommodationAvailabilityPort;
    private final LoadAccommodationReservationPort loadAccommodationReservationPort;
    private final SaveAccommodationReservationPort saveAccommodationReservationPort;

    private final AccommodationTypeQueryService accommodationTypeQueryService;


    @Override
    public UUID createAccommodationReservation(CreateAccommodationReservationCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = loadAccommodationAvailabilityPort.loadAccommodationAvailabilityByAccommodationId(command.getAccommodationId(), command.getStartDate(), command.getEndDate());
        accommodationTypeAvailability.verifyAccommodationAvailability(command.getAccommodationId(), command.getStartDate(), command.getEndDate());
        AccommodationReservation newAccommodationReservation = accommodationTypeAvailability.createReservation(command.getAccommodationId(), command.getStatus(), command.getStartDate(), command.getEndDate());
        saveAccommodationAvailabilityPort.saveAccommodationAvailability(accommodationTypeAvailability);
        return newAccommodationReservation.getAccommodationReservationId();
    }

    @Override
    public UUID createAccommodationTypeReservation(CreateAccommodationTypeReservationCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = loadAccommodationAvailabilityPort.loadAccommodationAvailabilityByAccommodationTypeId(command.getAccommodationTypeId(), command.getStartDate(), command.getEndDate());
        accommodationTypeAvailability.verifyAccommodationTypeAvailability(accommodationTypeAvailabilityPolicy, 1, command.getStartDate(), command.getEndDate());
        AccommodationTypeReservation newTypeReservation = accommodationTypeAvailability.createTypeReservation(command.getStartDate(), command.getEndDate());
        accommodationTypeAvailability.tryToAutoAssignTypeReservation(accommodationAssignmentPolicy, newTypeReservation);
        saveAccommodationAvailabilityPort.saveAccommodationAvailability(accommodationTypeAvailability);
        return newTypeReservation.getTypeReservationId();
    }

    @Override
    public void automaticAssign(AutomaticAssignAccommodationTypeReservationCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = loadAccommodationAvailabilityPort.loadAccommodationAvailabilityByAccommodationTypeReservationId(command.getAccommodationTypeReservationId());
        AccommodationTypeReservation accommodationTypeReservation = accommodationTypeAvailability.getAccommodationTypeReservationById(command.getAccommodationTypeReservationId());
        accommodationTypeAvailability.unassignTypeReservations(accommodationTypeReservation);
        accommodationTypeAvailability.verifyAccommodationTypeAvailabilityForTypeReservation(accommodationTypeAvailabilityPolicy, accommodationTypeReservation);
        accommodationTypeAvailability.tryToAutoAssignTypeReservation(accommodationAssignmentPolicy, accommodationTypeReservation);
        saveAccommodationAvailabilityPort.saveAccommodationAvailability(accommodationTypeAvailability);
    }

    @Override
    public void manualAssign(ManualAssignAccommodationTypeReservationCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = loadAccommodationAvailabilityPort.loadAccommodationAvailabilityByAccommodationTypeReservationId(command.getAccommodationTypeReservationId());
        AccommodationTypeReservation accommodationTypeReservation = accommodationTypeAvailability.getAccommodationTypeReservationById(command.getAccommodationTypeReservationId());
        accommodationTypeAvailability.tryToManualAssignTypeReservations(accommodationTypeReservation, command.getAccommodationReservations());
        saveAccommodationAvailabilityPort.saveAccommodationAvailability(accommodationTypeAvailability);
    }

    @Override
    public void unassign(UnassignAccommodationTypeReservationCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = loadAccommodationAvailabilityPort.loadAccommodationAvailabilityByAccommodationTypeReservationId(command.getAccommodationTypeReservationId());
        AccommodationTypeReservation accommodationTypeReservation = accommodationTypeAvailability.getAccommodationTypeReservationById(command.getAccommodationTypeReservationId());
        accommodationTypeAvailability.unassignTypeReservations(accommodationTypeReservation);
        saveAccommodationAvailabilityPort.saveAccommodationAvailability(accommodationTypeAvailability);
    }

    @Override
    public Boolean isAccommodationAvailable(IsAccommodationAvailableCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = loadAccommodationAvailabilityPort.loadAccommodationAvailabilityByAccommodationId(command.getAccommodationId(), command.getStartDate(), command.getEndDate());
        try {
            accommodationTypeAvailability.verifyAccommodationAvailability(command.getAccommodationId(), command.getStartDate(), command.getEndDate());
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public Boolean isAccommodationTypeAvailable(IsAccommodationTypeAvailableCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = loadAccommodationAvailabilityPort
                .loadAccommodationAvailabilityByAccommodationTypeId(
                        command.getAccommodationTypeId(),
                        command.getStartDate(),
                        command.getEndDate()
                );
        try {
            accommodationTypeAvailability.verifyAccommodationTypeAvailability(accommodationTypeAvailabilityPolicy,
                    command.getNumberOfAccommodations(), command.getStartDate(), command.getEndDate());
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public void removeAccommodationReservation(RemoveAccommodationReservationCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = loadAccommodationAvailabilityPort.loadAccommodationAvailabilityByAccommodationReservationId(command.getAccommodationReservationId());
        accommodationTypeAvailability.removeReservationById(command.getAccommodationReservationId());
        saveAccommodationAvailabilityPort.saveAccommodationAvailability(accommodationTypeAvailability);
    }

    @Override
    public void removeAccommodationTypeReservation(RemoveAccommodationTypeReservationCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = loadAccommodationAvailabilityPort.loadAccommodationAvailabilityByAccommodationTypeReservationId(command.getAccommodationTypeReservationId());
        accommodationTypeAvailability.removeTypeReservationById(command.getAccommodationTypeReservationId());
        saveAccommodationAvailabilityPort.saveAccommodationAvailability(accommodationTypeAvailability);
    }

    @Override
    public UUID create(CreateAccommodationTypeAvailabilityCommand command) {
        AccommodationTypeEntity accommodationTypeEntity = accommodationTypeQueryService.fetchById(command.getAccommodationTypeId());
        AccommodationTypeAvailability accommodationTypeAvailability = new AccommodationTypeAvailability(accommodationTypeEntity);
        return saveAccommodationAvailabilityPort.saveAccommodationAvailability(accommodationTypeAvailability);
    }

    @Override
    public void changeAccommodationReservationStatus(ChangeAccommodationReservationStatusCommand command) {
        AccommodationReservation accommodationReservation = loadAccommodationReservationPort.loadAccommodationReservation(command.getAccommodationReservationId());
        accommodationReservation.changeStatusTo(command.getStatus());
        saveAccommodationReservationPort.saveAccommodationReservation(accommodationReservation);
    }
}
