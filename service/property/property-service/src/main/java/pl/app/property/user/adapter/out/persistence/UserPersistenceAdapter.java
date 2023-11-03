package pl.app.property.user.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.user.adapter.out.persistence.mapper.UserMapper;
import pl.app.property.user.adapter.out.persistence.model.UserEntity;
import pl.app.property.user.adapter.out.persistence.repository.UserRepository;
import pl.app.property.user.application.domain.exception.UserException;
import pl.app.property.user.application.domain.model.User;
import pl.app.property.user.application.port.out.LoadUserPort;
import pl.app.property.user.application.port.out.SaveUserPort;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class UserPersistenceAdapter implements
        SaveUserPort,
        LoadUserPort {
    private final UserRepository repository;
    private final UserMapper mapper;


    @Override
    public User loadUser(UUID userId) {
        UserEntity entity = repository.findById(userId)
                .orElseThrow(() -> UserException.NotFoundUserException.fromId(userId));
        return mapper.mapToUser(entity);
    }

    @Override
    public User loadUserByEmail(String email) {
        UserEntity entity = repository.findByEmail(email)
                .orElseThrow(() -> UserException.NotFoundUserException.fromEmail(email));
        return mapper.mapToUser(entity);
    }

    @Override
    public UUID saveUser(User user) {
        UserEntity entity = mapper.mapToUserEntity(user);
        repository.save(entity);
        return entity.getUserId();
    }
}
