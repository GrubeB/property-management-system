package pl.app.property.registration_folio.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.registration_folio.adapter.out.persistence.mapper.RegistrationFolioMapper;
import pl.app.property.registration_folio.adapter.out.persistence.model.RegistrationFolioEntity;
import pl.app.property.registration_folio.adapter.out.persistence.repository.RegistrationFolioRepository;
import pl.app.property.registration_folio.application.domain.exception.RegistrationFolioException;
import pl.app.property.registration_folio.application.domain.model.RegistrationFolio;
import pl.app.property.registration_folio.application.port.out.LoadRegistrationFolioPort;
import pl.app.property.registration_folio.application.port.out.SaveRegistrationFolioPort;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
class RegistrationFolioPersistenceAdapter implements
        LoadRegistrationFolioPort,
        SaveRegistrationFolioPort {

    private final RegistrationFolioRepository registrationFolioRepository;
    private final RegistrationFolioMapper registrationFolioMapper;

    @Override
    public RegistrationFolio loadRegistrationFolio(UUID registrationFolioId) {
        RegistrationFolioEntity entity = registrationFolioRepository.findById(registrationFolioId)
                .orElseThrow(() -> RegistrationFolioException.NotFoundRegistrationFolioException.fromId(registrationFolioId));
        return registrationFolioMapper.mapToRegistrationFolio(entity);
    }

    @Override
    public RegistrationFolio loadRegistrationFolioByPartyFolio(UUID partyFolioId) {
        RegistrationFolioEntity entity = registrationFolioRepository.findByPartyFolios_PartyFolioId(partyFolioId)
                .orElseThrow(() -> RegistrationFolioException.NotFoundPartyFolioException.fromId(partyFolioId));
        return registrationFolioMapper.mapToRegistrationFolio(entity);
    }

    @Override
    public UUID saveRegistrationFolio(RegistrationFolio registrationFolio) {
        RegistrationFolioEntity entity = registrationFolioMapper.mapToRegistrationFolioEntity(registrationFolio);
        return registrationFolioRepository.save(entity).getRegistrationFolioId();
    }
}
