package pl.app.property.accommodation_availability.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation.adapter.out.query.AccommodationQueryService;
import pl.app.property.accommodation_availability.adapter.out.persistence.mapper.AccommodationAvailabilityMapper;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationReservationEntity;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.adapter.out.persistence.model.AccommodationTypeReservationEntity;
import pl.app.property.accommodation_availability.adapter.out.persistence.repository.AccommodationReservationEntityRepository;
import pl.app.property.accommodation_availability.adapter.out.persistence.repository.AccommodationTypeAvailabilityEntityRepository;
import pl.app.property.accommodation_availability.adapter.out.persistence.repository.AccommodationTypeReservationEntityRepository;
import pl.app.property.accommodation_availability.application.domain.exception.AccommodationAvailabilityException;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationReservation;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.port.out.LoadAccommodationAvailabilityPort;
import pl.app.property.accommodation_availability.application.port.out.LoadAccommodationReservationPort;
import pl.app.property.accommodation_availability.application.port.out.SaveAccommodationAvailabilityPort;
import pl.app.property.accommodation_availability.application.port.out.SaveAccommodationReservationPort;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
class AccommodationAvailabilityPersistenceAdapter implements
        LoadAccommodationAvailabilityPort,
        LoadAccommodationReservationPort,
        SaveAccommodationAvailabilityPort,
        SaveAccommodationReservationPort {
    private final AccommodationReservationEntityRepository accommodationReservationEntityRepository;
    private final AccommodationTypeReservationEntityRepository accommodationTypeReservationEntityRepository;
    private final AccommodationTypeAvailabilityEntityRepository accommodationTypeAvailabilityEntityRepository;
    private final AccommodationAvailabilityMapper accommodationAvailabilityMapper;
    private final AccommodationQueryService accommodationQueryService;

    @Override
    public AccommodationTypeAvailability loadAccommodationAvailabilityByAccommodationId(UUID accommodationId, LocalDate startDate, LocalDate endDate) {
        AccommodationTypeAvailabilityEntity accommodationTypeAvailabilityEntity = accommodationTypeAvailabilityEntityRepository.findByAccommodationType_Accommodation_AccommodationId(accommodationId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return this.accommodationAvailabilityMapper.mapToAccommodationTypeAvailability(accommodationTypeAvailabilityEntity, startDate, endDate);
    }

    @Override
    public AccommodationTypeAvailability loadAccommodationAvailabilityByAccommodationTypeReservationId(UUID accommodationTypeReservationId) {
        AccommodationTypeReservationEntity typeReservation = accommodationTypeReservationEntityRepository.findById(accommodationTypeReservationId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeReservationException.fromId(accommodationTypeReservationId));
        AccommodationTypeAvailabilityEntity accommodationTypeAvailabilityEntity = accommodationTypeAvailabilityEntityRepository.findByAccommodationTypeReservation_AccommodationTypeReservationId(accommodationTypeReservationId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return this.accommodationAvailabilityMapper.mapToAccommodationTypeAvailability(accommodationTypeAvailabilityEntity, typeReservation.getStartDate(), typeReservation.getEndDate());
    }

    @Override
    public AccommodationTypeAvailability loadAccommodationAvailabilityByAccommodationReservationId(UUID accommodationReservationId) {
        AccommodationReservationEntity accommodationReservationEntity = accommodationReservationEntityRepository.findById(accommodationReservationId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationReservationException.fromId(accommodationReservationId));
        AccommodationTypeAvailabilityEntity accommodationTypeAvailabilityEntity = accommodationTypeAvailabilityEntityRepository.findByAccommodationReservation_AccommodationReservationId(accommodationReservationId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return this.accommodationAvailabilityMapper.mapToAccommodationTypeAvailability(accommodationTypeAvailabilityEntity, accommodationReservationEntity.getStartDate(), accommodationReservationEntity.getEndDate());
    }

    @Override
    public AccommodationTypeAvailability loadAccommodationAvailabilityByAccommodationTypeId(UUID accommodationTypeId, LocalDate startDate, LocalDate endDate) {
        AccommodationTypeAvailabilityEntity accommodationTypeAvailabilityEntity = accommodationTypeAvailabilityEntityRepository.findByAccommodationType_AccommodationTypeId(accommodationTypeId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return this.accommodationAvailabilityMapper.mapToAccommodationTypeAvailability(accommodationTypeAvailabilityEntity, startDate, endDate);
    }

    @Override
    public AccommodationTypeAvailability loadAccommodationAvailability(UUID accommodationTypeAvailabilityId, LocalDate startDate, LocalDate endDate) {
        AccommodationTypeAvailabilityEntity accommodationTypeAvailabilityEntity = accommodationTypeAvailabilityEntityRepository.findByAccommodationTypeAvailabilityId(accommodationTypeAvailabilityId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException.fromId(accommodationTypeAvailabilityId));
        return this.accommodationAvailabilityMapper.mapToAccommodationTypeAvailability(accommodationTypeAvailabilityEntity, startDate, endDate);
    }

    @Override
    public AccommodationTypeAvailability loadAccommodationAvailability(UUID accommodationTypeAvailabilityId) {
        AccommodationTypeAvailabilityEntity accommodationTypeAvailabilityEntity = accommodationTypeAvailabilityEntityRepository.findByAccommodationTypeAvailabilityId(accommodationTypeAvailabilityId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException.fromId(accommodationTypeAvailabilityId));
        return this.accommodationAvailabilityMapper.mapToAccommodationTypeAvailability(accommodationTypeAvailabilityEntity, LocalDate.MIN, LocalDate.MAX);
    }


    @Override
    public AccommodationReservation loadAccommodationReservation(UUID accommodationReservationId) {
        AccommodationReservationEntity reservationEntity = accommodationReservationEntityRepository.findById(accommodationReservationId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationReservationException.fromId(accommodationReservationId));
        return accommodationAvailabilityMapper.mapToAccommodationReservation(reservationEntity);
    }

    @Override
    public UUID saveAccommodationAvailability(AccommodationTypeAvailability domain) {
        AccommodationTypeAvailabilityEntity entity = accommodationAvailabilityMapper.mapToAccommodationTypeAvailabilityEntity(domain);
        AccommodationTypeAvailabilityEntity savedEntity = accommodationTypeAvailabilityEntityRepository.save(entity);
        return savedEntity.getAccommodationTypeAvailabilityId();
    }

    @Override
    public UUID saveAccommodationReservation(AccommodationReservation domain) {
        AccommodationReservationEntity accommodationReservationEntity = accommodationReservationEntityRepository.findById(domain.getAccommodationReservationId())
                .orElse(new AccommodationReservationEntity());
        accommodationReservationEntity.setAccommodationReservationId(domain.getAccommodationReservationId());
        accommodationReservationEntity.setAccommodation(accommodationQueryService.fetchById(domain.getAccommodationId()));
        accommodationReservationEntity.setStatus(domain.getStatus());
        accommodationReservationEntity.setStartDate(domain.getStartDate());
        accommodationReservationEntity.setEndDate(domain.getEndDate());
        return accommodationReservationEntityRepository.save(accommodationReservationEntity).getAccommodationReservationId();
    }
}
