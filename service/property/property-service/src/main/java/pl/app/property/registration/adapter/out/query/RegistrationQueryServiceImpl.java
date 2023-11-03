package pl.app.property.registration.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.registration.adapter.out.persistence.repository.RegistrationRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class RegistrationQueryServiceImpl implements RegistrationQueryService {
    private final RegistrationRepository repository;
    private final RegistrationRepository specificationRepository;
    private final ModelMapper modelMapper;
}
