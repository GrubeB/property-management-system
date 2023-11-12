package pl.app.property.registration_folio.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.registration_folio.adapter.out.persistence.model.RegistrationFolioEntity;
import pl.app.property.registration_folio.adapter.out.query.RegistrationFolioQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(RegistrationFolioByPropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class RegistrationFolioByPropertyQueryController implements
        QueryController.DtoFetchableWithFilter.Full<UUID, RegistrationFolioEntity> {
    public static final String resourceName = "registration-folios";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "propertyId", "registration.property.propertyId"
    );

    public final RegistrationFolioQueryService service;
}
