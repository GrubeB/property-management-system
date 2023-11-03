package pl.app.property.guest.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.guest.persistence.GuestRepository;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class GuestServiceImpl implements GuestService {
    private final GuestRepository repository;
    private final PropertyQueryService propertyQueryService;

    @Override
    public void settingParentBeforeSave(UUID parentId, GuestEntity entity) {
        PropertyEntity property = propertyQueryService.fetchById(parentId);
        entity.setProperty(property);
    }
}
