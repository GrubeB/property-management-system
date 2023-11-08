package pl.app.property.registration.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationEntity;
import pl.app.property.registration.adapter.out.query.RegistrationQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(RegistrationByPropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class RegistrationByPropertyQueryController implements
        QueryController.DtoFetchableWithFilter<UUID, RegistrationEntity> {
    public static final String resourceName = "registrations";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "propertyId", "property.propertyId"
    );

    public final RegistrationQueryService service;
}
