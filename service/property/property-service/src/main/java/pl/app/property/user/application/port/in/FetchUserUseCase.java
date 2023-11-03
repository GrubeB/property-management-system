package pl.app.property.user.application.port.in;

import pl.app.property.user.application.domain.model.User;

public interface FetchUserUseCase {
    User fetchUser(FetchUserCommand command);
}
