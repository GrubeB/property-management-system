package pl.app.property.reservation_folio.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioEntity;
import pl.app.property.reservation_folio.adapter.out.query.ReservationFolioQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(ReservationFolioByPropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class ReservationFolioByPropertyQueryController implements
        QueryController.DtoFetchableWithFilter.Full<UUID, ReservationFolioEntity> {
    public static final String resourceName = "reservation-folios";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "propertyId", "reservation.property.propertyId"
    );
    private final ReservationFolioQueryService service;

}
