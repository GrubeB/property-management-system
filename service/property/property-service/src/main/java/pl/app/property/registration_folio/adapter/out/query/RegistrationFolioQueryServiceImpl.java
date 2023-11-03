package pl.app.property.registration_folio.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.registration_folio.adapter.out.persistence.repository.RegistrationFolioRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class RegistrationFolioQueryServiceImpl implements RegistrationFolioQueryService {
    private final RegistrationFolioRepository repository;
    private final RegistrationFolioRepository specificationRepository;
    private final ModelMapper modelMapper;
}
