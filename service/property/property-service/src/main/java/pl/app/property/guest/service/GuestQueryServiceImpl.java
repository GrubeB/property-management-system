package pl.app.property.guest.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.guest.persistence.GuestRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class GuestQueryServiceImpl implements GuestQueryService {
    private final GuestRepository repository;
    private final GuestRepository specificationRepository;
}
