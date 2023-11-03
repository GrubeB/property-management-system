package pl.app.property.user.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.user.adapter.out.persistence.model.UserEntity;
import pl.app.property.user.adapter.out.query.UserQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(UserByOrganizationQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class UserByOrganizationQueryController implements
        QueryController.DtoFetchableWithFilter<UUID, UserEntity> {
    public static final String resourceName = "users";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/" + resourceName;
    private final Map<String, String> parentFilterMap = Map.of(
            "organizationId", "organizations.organizationId"
    );
    private final UserQueryService service;
}
