package pl.app.property.reservation.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.reservation.adapter.out.persistence.model.ReservationEntity;
import pl.app.property.reservation.adapter.out.query.ReservationQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(ReservationByPropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class ReservationByPropertyQueryController implements
        QueryController.DtoFetchableWithFilter<UUID, ReservationEntity> {
    public static final String resourceName = "reservations";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "propertyId", "property.propertyId"
    );

    private final ReservationQueryService service;
}
