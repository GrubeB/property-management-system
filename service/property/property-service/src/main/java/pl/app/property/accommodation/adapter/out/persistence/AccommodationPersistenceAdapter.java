package pl.app.property.accommodation.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation.adapter.out.persistence.mapper.AccommodationMapper;
import pl.app.property.accommodation.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.accommodation.adapter.out.persistence.repository.AccommodationRepository;
import pl.app.property.accommodation.application.domain.exception.AccommodationException;
import pl.app.property.accommodation.application.domain.model.Accommodation;
import pl.app.property.accommodation.application.port.out.LoadAccommodationPort;
import pl.app.property.accommodation.application.port.out.RemoveAccommodationPort;
import pl.app.property.accommodation.application.port.out.SaveAccommodationPort;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AccommodationPersistenceAdapter implements
        LoadAccommodationPort,
        RemoveAccommodationPort,
        SaveAccommodationPort {

    private final AccommodationMapper mapper;
    private final AccommodationRepository repository;

    @Override
    public void removeAccommodation(UUID accommodationId) {
        repository.deleteById(accommodationId);
    }

    @Override
    public UUID saveAccommodation(Accommodation accommodation) {
        AccommodationEntity entity = mapper.mapToAccommodationEntity(accommodation);
        return repository.save(entity).getAccommodationId();
    }

    @Override
    public Accommodation loadAccommodation(UUID accommodationId) {
        AccommodationEntity entity = repository.findById(accommodationId)
                .orElseThrow(() -> AccommodationException.NotFoundAccommodationException.fromId(accommodationId));
        return mapper.mapToAccommodation(entity);
    }
}
