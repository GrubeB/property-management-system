package pl.app.property.accommodation.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation.application.domain.model.Accommodation;
import pl.app.property.accommodation.application.port.in.AddAccommodationCommand;
import pl.app.property.accommodation.application.port.in.AddAccommodationUseCase;
import pl.app.property.accommodation.application.port.in.RemoveAccommodationCommand;
import pl.app.property.accommodation.application.port.in.RemoveAccommodationUseCase;
import pl.app.property.accommodation.application.port.out.LoadAccommodationPort;
import pl.app.property.accommodation.application.port.out.RemoveAccommodationPort;
import pl.app.property.accommodation.application.port.out.SaveAccommodationPort;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
class AccommodationService implements
        AddAccommodationUseCase,
        RemoveAccommodationUseCase {
    private final LoadAccommodationPort loadAccommodationPort;
    private final RemoveAccommodationPort removeAccommodationPort;
    private final SaveAccommodationPort saveAccommodationPort;

    @Override
    public UUID addAccommodation(AddAccommodationCommand command) {
        Accommodation newAccommodation = new Accommodation(command.getName(), command.getDescription(), command.getAccommodationTypeId());
        return saveAccommodationPort.saveAccommodation(newAccommodation);
    }

    @Override
    public void removeAccommodation(RemoveAccommodationCommand command) {
        removeAccommodationPort.removeAccommodation(command.getAccommodationId());
    }
}
