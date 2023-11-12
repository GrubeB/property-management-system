package pl.app.property.reservation_payment_policy.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.model.ReservationPaymentPolicyEntity;
import pl.app.property.reservation_payment_policy.adapter.out.query.ReservationPaymentPolicyQueryService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(ReservationPaymentPolicyByPropertyQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class ReservationPaymentPolicyByPropertyQueryController implements
        QueryController.DtoFetchableWithFilter.Full<UUID, ReservationPaymentPolicyEntity> {
    public static final String resourceName = "reservation-payment-policies";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/" + resourceName;

    private final Map<String, String> parentFilterMap = Map.of(
            "propertyId", "property.propertyId"
    );

    private final ReservationPaymentPolicyQueryService service;
}
