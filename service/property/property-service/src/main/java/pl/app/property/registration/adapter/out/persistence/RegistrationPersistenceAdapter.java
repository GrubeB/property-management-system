package pl.app.property.registration.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.registration.adapter.out.persistence.mapper.RegistrationMapper;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationEntity;
import pl.app.property.registration.adapter.out.persistence.repository.RegistrationRepository;
import pl.app.property.registration.application.domain.exception.RegistrationException;
import pl.app.property.registration.application.domain.model.Registration;
import pl.app.property.registration.application.port.out.LoadRegistrationPort;
import pl.app.property.registration.application.port.out.SaveRegistrationPort;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationPersistenceAdapter implements
        LoadRegistrationPort,
        SaveRegistrationPort {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;

    @Override
    public Registration loadRegistration(UUID registrationId) {
        RegistrationEntity registrationEntity = registrationRepository.findById(registrationId)
                .orElseThrow(() -> RegistrationException.NotFoundRegistrationException.fromId(registrationId));
        return registrationMapper.mapToRegistration(registrationEntity);
    }

    @Override
    public UUID saveRegistration(Registration registration) {
        RegistrationEntity registrationEntity = registrationMapper.mapToRegistrationEntity(registration);
        return registrationRepository.saveAndFlush(registrationEntity).getRegistrationId();
    }
}
