package pl.app.property.user.application.port.out;


import pl.app.property.user.application.domain.model.User;

import java.util.UUID;

public interface SaveUserPort {
    UUID saveUser(User user);
}
