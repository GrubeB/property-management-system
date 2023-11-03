package pl.app.property.user.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.user.adapter.out.persistence.model.UserEntity;
import pl.app.property.user.adapter.out.query.UserQueryService;

import java.util.UUID;

@RestController
@RequestMapping(UserQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class UserQueryController implements
        QueryController.DtoFetchable<UUID, UserEntity> {
    public static final String resourceName = "users";
    public static final String resourcePath = "/api/v1/" + resourceName;
    private final UserQueryService service;
}
