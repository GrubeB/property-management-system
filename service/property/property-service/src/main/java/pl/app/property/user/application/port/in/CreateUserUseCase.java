package pl.app.property.user.application.port.in;

import java.util.UUID;

public interface CreateUserUseCase {
    UUID createUser(CreateUserCommand command);
}
